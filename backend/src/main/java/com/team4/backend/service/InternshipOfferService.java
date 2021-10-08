package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.util.ValidatingPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

import java.time.LocalDateTime;

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

    public Flux<InternshipOffer> getStudentExclusiveOffers(String studentEmail, Integer page, Integer size) {
        PageRequest pageRequest;
        try {
            pageRequest = new ValidatingPageRequest(page, size).getPageRequest();
        } catch (InvalidPageRequestException e) {
            return Flux.error(e);
        }

        return studentService.getStudent(studentEmail)
                .switchIfEmpty(
                        Mono.error(
                                new UserNotFoundException("Could not find student with email: " + studentEmail)
                        )
                )
                .map(Student::getExclusiveOffersId)
                .flatMapMany(offerIdList ->
                        Flux.fromIterable(offerIdList)
                                .skip(pageRequest.getOffset())
                                .take(pageRequest.getPageSize())
                                .flatMap(offerId ->
                                        internshipOfferRepository.
                                                findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                                                        offerId,
                                                        LocalDate.now()
                                                )
                                )
                ).delayElements(Duration.ofSeconds(3));
    }

    public Flux<InternshipOffer> getGeneralInternshipOffers(Integer page, Integer size) {

        PageRequest pageRequest;
        try {
            pageRequest = new ValidatingPageRequest(page, size).getPageRequest();
        } catch (InvalidPageRequestException e) {
            return Flux.error(e);
        }

        return internshipOfferRepository
                .findAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now(), pageRequest);

    }

    public Flux<InternshipOffer> getNonValidatedInternshipOffers() {
        return internshipOfferRepository.findAllInternshipOfferByIsValidatedFalse();
    }
    public Flux<InternshipOffer> getNotYetValidatedInternshipOffers() {
        return internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();
    }

    public Mono<InternshipOffer> validateInternshipOffer(String id){
        return internshipOfferRepository.findById(id).map(offer -> {
            offer.setValidated(true);
            offer.setValidationDate(LocalDateTime.now());
            return offer;
        }).flatMap(internshipOfferRepository::save)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't find InternshipOffer with this id.")));
    }

    public Mono<InternshipOffer> refuseInternshipOffer(String id){
        return internshipOfferRepository.findById(id).map(offer -> {
            offer.setValidated(false);
            offer.setValidationDate(LocalDateTime.now());
            return offer;
        }).flatMap(internshipOfferRepository::save)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't find InternshipOffer with this id.")));
    }
    public Mono<Long> getInternshipOffersPageCount(Integer size) {
        if(size < 1) {
            return Mono.error(InvalidPageRequestException::new);
        }

        return internshipOfferRepository.countAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now())
                .map(count -> (long)Math.ceil((double)count / (double)size));
    }

    public Mono<Long> getInternshipOffersPageCount(String studentEmail, Integer size) {
        if(size < 1) {
            return Mono.error(InvalidPageRequestException::new);
        }
        if(studentEmail == null) {
            return Mono.error(new UserNotFoundException("Email is null"));
        }

        return studentService.getStudent(studentEmail)
                .switchIfEmpty(
                        Mono.error(new UserNotFoundException("Could not find student with email: " + studentEmail))
                )
                .map(student -> student.getExclusiveOffersId().size())
                .map(count -> (long)Math.ceil((double)count / (double)size));
    }
}
