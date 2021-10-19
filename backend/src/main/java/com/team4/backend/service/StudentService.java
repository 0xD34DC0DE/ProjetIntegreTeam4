package com.team4.backend.service;

import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    public StudentService(StudentRepository studentRepository, PBKDF2Encoder pbkdf2Encoder, UserService userService) {
        this.studentRepository = studentRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
    }

    public Mono<Student> registerStudent(Student student) {
        return userService.existsByEmail(student.getEmail()).flatMap(exists -> {
            if (!exists) {
                student.setPassword(pbkdf2Encoder.encode(student.getPassword()));
                return studentRepository.save(student);
            } else {
                return Mono.error(new UserAlreadyExistsException("User already exist"));
            }
        });
    }

    public Mono<Student> getStudent(String studentEmail) {
        return studentRepository.findByEmailAndIsEnabledTrue(studentEmail);
    }

    public Mono<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find user with this email")));
    }

    public Mono<Student> updateCvValidity(String email, Boolean valid) {
        return findByEmail(email).map(student -> {
            student.setHasValidCv(valid);
            return student;
        }).flatMap(studentRepository::save);
    }

    public Mono<Student> updateStudentState(String email, StudentState studentState) {
        return findByEmail(email)
                .filter(student -> student.getStudentState().equals(StudentState.WAITING_FOR_RESPONSE))
                .switchIfEmpty(Mono.error(new ForbiddenActionException("Can't update your state if you're not waiting for a response to your recent interview!")))
                .map(student -> {
                    //TODO --> call function that will trigger the contract generation
                    student.setStudentState(studentState);
                    return student;
                }).flatMap(studentRepository::save);
    }

    public Mono<Student> addOfferToStudentAppliedOffers(Student student, String offerId) {
        if (!student.getAppliedOffersId().contains(offerId)) {
            student.getAppliedOffersId().add(offerId);
            return studentRepository.save(student);
        }
        return Mono.just(student);
    }

}
