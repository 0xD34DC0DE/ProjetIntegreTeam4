package com.team4.backend.service;

import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.util.PBKDF2Encoder;
import com.team4.backend.util.SemesterUtil;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.team4.backend.model.enums.StudentState.*;

@Log
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    private final SemesterService semesterService;

    public StudentService(StudentRepository studentRepository,
                          PBKDF2Encoder pbkdf2Encoder,
                          UserService userService,
                          SemesterService semesterService) {
        this.studentRepository = studentRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
        this.semesterService = semesterService;
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
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find student with email" + email)));
    }

    public Flux<Student> findAllByEmails(Set<String> emails) {
        return studentRepository.findAllByEmails(emails);
    }

    public Flux<Student> findAllByIds(List<String> ids) {
        return studentRepository.findAllByIds(ids);
    }

    public Mono<Student> updateCvValidity(String email, Boolean valid) {
        return findByEmail(email).map(student -> {
            student.setHasValidCv(valid);
            return student;
        }).flatMap(studentRepository::save);
    }

    public Mono<Student> updateStudentState(String email, StudentState studentState) {
        return findByEmail(email)
                .filter(student -> student.getStudentState().equals(StudentState.WAITING_FOR_RESPONSE)
                        && student.getHasValidCv())
                .switchIfEmpty(Mono.error(new ForbiddenActionException("Can't update your state if you're not waiting for a response to your recent interview!")))
                .map(student -> {
                    // TODO --> call function that will trigger the contract generation
                    student.setStudentState(studentState);
                    return student;
                }).flatMap(studentRepository::save);
    }

    public Mono<Student> updateInterviewDate(String email, LocalDate interviewDate) {
        return findByEmail(email)
                .filter(student -> !student.getStudentState().equals(StudentState.INTERNSHIP_FOUND) &&
                        student.getHasValidCv())
                .switchIfEmpty(Mono.error(new ForbiddenActionException("Can't add an interview if you already have an internship for this semester!")))
                .flatMap(student -> semesterService.getCurrentSemester()
                        .filter(semester -> SemesterUtil
                                .checkIfDatesAreInsideRangeOfSemester(semester, interviewDate.atStartOfDay(), interviewDate.atStartOfDay())
                        )
                        .switchIfEmpty(Mono.error(new ForbiddenActionException("Can't add an interview that is not inside the range of this semester!")))
                        .map((semester) -> {
                            student.getInterviewsDate().add(interviewDate);
                            return student;
                        })

                ).flatMap(studentRepository::save);

    }

    public Mono<Student> addOfferToStudentAppliedOffers(Student student, String offerId) {
        if (!student.getAppliedOffersId().contains(offerId)) {
            student.getAppliedOffersId().add(offerId);
            return studentRepository.save(student);
        }
        return Mono.just(student);
    }

    public Mono<Student> setHasCvStatusTrue(String email) {
        return studentRepository.findByEmail(email).flatMap(student -> {
            if (!student.getHasCv()) {
                student.setHasCv(true);
            }
            return studentRepository.save(student);
        });
    }

    public Flux<Student> getAll() {
        return studentRepository.findAllByRole("STUDENT");
    }

    public Flux<Student> getAllStudentsWithNoCv() {
        return studentRepository.findAllByHasCvFalse();
    }

    public Flux<Student> getAllStudentsWithUnvalidatedCv() {
        return studentRepository.findAllByHasValidCvFalse();
    }

    public Flux<Student> getStudentsNoInternship() {
        return studentRepository.findAllByStudentState(REGISTERED);
    }

    public Flux<Student> getStudentsWaitingInterview() {
        return studentRepository.findAllByStudentState(INTERNSHIP_NOT_FOUND);
    }

    public Flux<Student> getStudentsWaitingResponse() {
        return studentRepository.findAllByStudentState(WAITING_FOR_RESPONSE);
    }

    public Flux<Student> getStudentsWithInternship() {
        return studentRepository.findAllByStudentState(INTERNSHIP_FOUND);
    }

    public Mono<Student> findById(String studentId) {
        return studentRepository.findById(studentId)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Could not find student with id: " + studentId)));
    }

    public Flux<Student> getAllWithNoEvaluationDateDuringSemester(String semesterFullName) {

        return semesterService
                .findByFullName(semesterFullName)
                .flatMapMany(semester -> studentRepository.findAllByStudentState(INTERNSHIP_FOUND)
                        .filter(student ->
                                student.getEvaluationsDates().isEmpty() ||
                                        student.getEvaluationsDates()
                                                .stream()
                                                .anyMatch(date -> date.atStartOfDay().isBefore(semester.getFrom()) &&
                                                        date.atStartOfDay().isAfter(semester.getTo())
                                                )


                        )
                );
    }

    public Mono<Long> updateStudentStateForAllStudentThatInterviewDateHasPassed() {
        return studentRepository.findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState.INTERNSHIP_NOT_FOUND)
                .map(student -> {
                    student.setStudentState(StudentState.WAITING_FOR_RESPONSE);
                    log.info("STATE UPDATED : " + student.getFirstName() + ", " + student.getLastName());
                    return save(student).subscribe();
                }).count();
    }

    public Mono<Long> resetStudentStateForAllStudentWithInternship() {
        return getStudentsWithInternship()
                .map(student -> {
                    log.info("RESET STATE : " + student.getFirstName() + ", " + student.getLastName());
                    student.setStudentState(INTERNSHIP_NOT_FOUND);
                    return save(student).subscribe();
                }).count();
    }

    public Mono<Student> save(Student student) {
        return studentRepository.save(student);
    }

}
