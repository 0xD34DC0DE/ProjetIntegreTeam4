package com.team4.backend.service;

import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipContract;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;

    private final InternshipOfferService internshipOfferService;

    private final StudentService studentService;

    public InternshipService(InternshipRepository internshipRepository,
                             InternshipOfferService internshipOfferService,
                             StudentService studentService) {
        this.internshipRepository = internshipRepository;
        this.internshipOfferService = internshipOfferService;
        this.studentService = studentService;
    }

    public Mono<Internship> createInternship(InternshipCreationDto internshipCreationDto) {
        Mono<Student> studentMono = studentService.findByEmail(internshipCreationDto.getStudentEmail());
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService
                .findInternshipOfferById(internshipCreationDto.getInternshipOfferId());

        return Mono.zip(studentMono, internshipOfferMono)
                .map(tuple -> {
                    Student student = tuple.getT1();
                    InternshipOffer internshipOffer = tuple.getT2();

                    return Internship.builder()
                            .monitorEmail(internshipOffer.getMonitorEmail())
                            .internshipManagerEmail(internshipOffer.getEmailOfApprovingInternshipManager())
                            .studentEmail(student.getEmail())
                            .internshipContract(new InternshipContract())
                            .beginningDate(internshipOffer.getBeginningDate())
                            .endingDate(internshipOffer.getEndingDate())
                            .build();
                })
                .flatMap(internshipRepository::save);
    }
}
