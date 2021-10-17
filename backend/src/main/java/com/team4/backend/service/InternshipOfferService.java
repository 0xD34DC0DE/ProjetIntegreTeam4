package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.exception.InternshipOfferNotFoundException;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.security.UserSessionService;
import com.team4.backend.util.ExperimentalValidatingPageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
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

    public Mono<InternshipOffer> findInternshipOfferById(String offerId) {
        return internshipOfferRepository.findById(offerId)
                .switchIfEmpty(
                        Mono.error(new InternshipOfferNotFoundException("Can't find internship offer with this id"))
                );
    }

    public Mono<InternshipOffer> addAnInternshipOffer(InternshipOfferCreationDto internshipOfferDTO) {
        return monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())
                .flatMap(exist -> exist ?
                        internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO))
                        : Mono.error(new UserNotFoundException("Can't find monitor!")));
    }

    public Flux<InternshipOffer> getStudentExclusiveOffers(String studentEmail, Integer page, Integer size) {
        return studentService.findByEmail(studentEmail)
                .map(Student::getExclusiveOffersId)
                .flatMapMany(offerIdList ->
                        ExperimentalValidatingPageRequest.applyPaging(offerIdList, page, size)
                                .flatMap(offerId ->
                                        internshipOfferRepository.
                                                findByIdAndIsExclusiveTrueAndLimitDateToApplyAfterAndIsValidatedTrue(
                                                        offerId,
                                                        LocalDate.now()
                                                )
                                ));
    }

    public Flux<InternshipOffer> getGeneralInternshipOffers(Integer page, Integer size) {
        return ExperimentalValidatingPageRequest.getPageRequestMono(page, size)
                .flatMapMany(pageRequest ->
                        internshipOfferRepository
                                .findAllByIsExclusiveFalseAndLimitDateToApplyAfterAndIsValidatedTrue(
                                        LocalDate.now(),
                                        pageRequest
                                )
                );
    }

    public Flux<InternshipOffer> getNotYetValidatedInternshipOffers() {
        return internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();
    }

    public Mono<InternshipOffer> validateInternshipOffer(String id, Boolean isValid) {
        return findInternshipOfferById(id)
                .map(offer -> {
                    offer.setIsValidated(isValid);
                    offer.setValidationDate(LocalDateTime.now());

                    return offer;
                }).flatMap(internshipOfferRepository::save);
    }

    public Mono<Long> getInternshipOffersPageCount(Integer size) {
        if (size < 1) {
            return Mono.error(InvalidPageRequestException::new);
        }

        return internshipOfferRepository.countAllByIsExclusiveFalseAndLimitDateToApplyAfter(LocalDate.now())
                .map(count -> (long) Math.ceil((double) count / (double) size));
    }

    public Mono<Long> getInternshipOffersPageCount(String studentEmail, Integer size) {
        if (size < 1) {
            return Mono.error(InvalidPageRequestException::new);
        }
        if (studentEmail == null) {
            return Mono.error(new UserNotFoundException("Email is null"));
        }

        return studentService.findByEmail(studentEmail)
                .map(student -> student.getExclusiveOffersId().size())
                .map(count -> (long) Math.ceil((double) count / (double) size));
    }

    public Mono<InternshipOffer> applyOffer(String offerId, Principal principal) {
        return findInternshipOfferById(offerId)
                .flatMap(internshipOffer -> {
                            if(!internshipOffer.getIsValidated()) {
                                return Mono.error(new UnauthorizedException("Cannot apply to unvalidated offers"));
                            }
                            return studentService.findByEmail(UserSessionService.getLoggedUserEmail(principal))
                                    .flatMap(student ->
                                            addStudentEmailToOfferInterestedStudents(internshipOffer, student)
                                    );
                        }
                );
    }

    private Mono<InternshipOffer> addStudentEmailToOfferInterestedStudents(InternshipOffer internshipOffer,
                                                                           Student student) {
        return Mono.just(internshipOffer).flatMap(offer -> {
            if (offer.getIsExclusive()) {
                if (!student.getExclusiveOffersId().contains(offer.getId())) {
                    return Mono.error(
                            new UnauthorizedException("Student is not allowed to apply to this exclusive offer")
                    );
                }
            }
            return Mono.just(offer);
        }).flatMap(offer -> {
            if (!offer.getListEmailInterestedStudents().contains(student.getEmail())) {
                offer.getListEmailInterestedStudents().add(student.getEmail());
                return studentService.addOfferToStudentAppliedOffers(student, offer.getId()).then(
                    internshipOfferRepository.save(offer)
                );
            }
            return Mono.just(offer);
        });
    }
}
