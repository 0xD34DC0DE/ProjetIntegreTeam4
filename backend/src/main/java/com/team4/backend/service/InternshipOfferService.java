package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    private final MonitorService monitorService;

    private final StudentService studentService;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository,
                                  MonitorService monitorService,
                                  StudentService studentService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.monitorService = monitorService;
        this.studentService = studentService;
    }

    public Mono<InternshipOffer> addAnInternshipOffer(InternshipOfferCreationDto internshipOfferDTO) {
        return monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())
                .flatMap(exist -> exist ?
                        internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO))
                        : Mono.error(new UserNotFoundException("Can't find monitor!")));
    }

    private static Logger logger = LoggerFactory.getLogger(InternshipOfferService.class);
    public Flux<InternshipOffer> getStudentInternshipOffers(String studentEmail) {
        return Flux.mergeSequential(
                studentService.getStudent(studentEmail)
                        .switchIfEmpty(
                                Mono.error(
                                        new UserNotFoundException("Could not find student with email: " + studentEmail)
                                )
                        )
                        .map(Student::getExclusiveOffersId)
                        .flatMapMany(offerIdList ->
                                Flux.fromIterable(offerIdList)
                                        .flatMap(offerId ->
                                                internshipOfferRepository.
                                                        findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                                                                offerId,
                                                                LocalDate.now()
                                                        ).map(s -> {
                                                            logger.warn(s.toString()); return s;})
                                                .switchIfEmpty(Mono.error(new Exception("Fak")))
                                        )
                        ),
                internshipOfferRepository
                        .findAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now())
        );
    }
}
