package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
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

    public Flux<InternshipOffer> getStudentExclusiveOffers(String studentEmail, @Min(0) Integer page, @Min(1) Integer size) {
        return studentService.getStudent(studentEmail)
                .switchIfEmpty(
                        Mono.error(
                                new UserNotFoundException("Could not find student with email: " + studentEmail)
                        )
                )
                .map(Student::getExclusiveOffersId)
                .flatMapMany(offerIdList ->
                        Flux.fromIterable(offerIdList)
                                .skip(size * page)
                                .take(size)
                                .flatMap(offerId ->
                                        internshipOfferRepository.
                                                findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                                                        offerId,
                                                        LocalDate.now()
                                                )
                                )
                ).delayElements(Duration.ofSeconds(3));
    }

    public Flux<InternshipOffer> getGeneralInternshipOffers(@Min(0) Integer page, @Min(1) Integer size) {
        return internshipOfferRepository
                .findAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now(), PageRequest.of(page, size));

    }

    public Mono<Long> getInternshipOffersPageCount(@Min(1) Integer size) {
        return internshipOfferRepository.countAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now())
                .map(count -> (long)Math.ceil((double)count / (double)size));
    }

    public Mono<Long> getInternshipOffersPageCount(@NotNull String studentEmail, @Min(1) Integer size) {
        return studentService.getStudent(studentEmail)
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("Could not find student with email: " + studentEmail))
                )
                .map(student -> student.getExclusiveOffersId().size())
                .map(count -> (long)Math.ceil((double)count / (double)size));
    }
}
