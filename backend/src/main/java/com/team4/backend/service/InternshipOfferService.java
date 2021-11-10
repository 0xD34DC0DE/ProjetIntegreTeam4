package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.InternshipOfferNotFoundException;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.util.ValidatingPageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;
    private final MonitorService monitorService;
    private final StudentService studentService;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository, MonitorService monitorService,
                                  StudentService studentService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.monitorService = monitorService;
        this.studentService = studentService;
    }

    public Mono<InternshipOffer> findInternshipOfferById(String offerId) {
        return internshipOfferRepository.findById(offerId).switchIfEmpty(
                Mono.error(new InternshipOfferNotFoundException("Can't find internship offer with this id")));
    }

    public Mono<InternshipOffer> addAnInternshipOffer(InternshipOfferCreationDto internshipOfferDTO) {
        return monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getMonitorEmail())
                .flatMap(exist -> exist
                        ? internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO))
                        : Mono.error(new UserNotFoundException("Can't find monitor!")));
    }

    public Flux<InternshipOfferStudentViewDto> getStudentExclusiveOffers(String studentEmail, Integer page,
                                                                         Integer size) {
        return studentService.findByEmail(studentEmail).flatMapMany(student -> ValidatingPageRequest
                .applyPaging(student.getExclusiveOffersId(), page, size)
                .flatMap(offerId -> internshipOfferRepository
                        .findByIdAndIsExclusiveTrueAndLimitDateToApplyAfterAndIsValidatedTrue(offerId, LocalDate.now()))
                .map(InternshipOfferMapper::toStudentViewDto).map(internshipOfferDto -> {
                    if (student.getAppliedOffersId().contains(internshipOfferDto.getId())) {
                        internshipOfferDto.setHasAlreadyApplied(true);
                    }
                    return internshipOfferDto;
                }));
    }


    public Flux<InternshipOfferStudentViewDto> getGeneralInternshipOffers(Integer page, Integer size,
                                                                          String studentEmail) {

        //TODO : get current session date range to pass to new query of internshipOfferRepository
        return studentService.findByEmail(studentEmail)
                .flatMapMany(student -> ValidatingPageRequest.getPageRequestMono(page, size)
                        .flatMapMany(pageRequest -> internshipOfferRepository
                                .findAllByIsExclusiveFalseAndLimitDateToApplyAfterAndIsValidatedTrue(LocalDate.now(),
                                        pageRequest))
                        .map(InternshipOfferMapper::toStudentViewDto).flatMap(internshipOfferDto -> {
                            if (student.getAppliedOffersId().contains(internshipOfferDto.getId())) {
                                internshipOfferDto.setHasAlreadyApplied(true);
                            }
                            return Mono.just(internshipOfferDto);
                        }));
    }

    // TODO : refactor to add session name in argument
    //TODO : call SessionService.findByName and pass range of date to new query
    public Flux<InternshipOffer> getNotYetValidatedInternshipOffers() {
        return internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();
    }

    public Mono<InternshipOffer> validateInternshipOffer(String id, Boolean isValid) {
        return findInternshipOfferById(id).map(offer -> {
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

        return studentService.findByEmail(studentEmail).map(student -> student.getExclusiveOffersId().size())
                .map(count -> (long) Math.ceil((double) count / (double) size));
    }

    public Mono<Boolean> isStudentEmailInMonitorOffersInterestedStudents(String studentEmail, String monitorEmail) {
        return internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrue(monitorEmail).collectList()
                .flatMapMany(allFoundInternshipOffer -> {
                    for (InternshipOffer internshipOffer : allFoundInternshipOffer) {
                        if (internshipOffer.getListEmailInterestedStudents().contains(studentEmail)) {
                            return Mono.just(true);
                        }
                    }
                    return Mono.just(false);
                }).next().flatMap(Mono::just);
    }

    public Mono<InternshipOffer> applyOffer(String offerId, String studentEmail) {
        return findInternshipOfferById(offerId).flatMap(internshipOffer -> {
            if (!internshipOffer.getIsValidated()) {
                return Mono.error(new UnauthorizedException("Cannot apply to unvalidated offers"));
            }
            return studentService.findByEmail(studentEmail)
                    .flatMap(student -> addStudentEmailToOfferInterestedStudents(internshipOffer, student));
        });
    }

    // TODO :call SessionService.findBySessionName()
    //TODO: refactor internshipOfferRepositoryQuery to add DateBetween and pass the range of the session
    public Flux<InternshipOfferStudentInterestViewDto> getInterestedStudents(String monitorEmail) {
        return internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrue(monitorEmail)
                .filter(internshipOffer -> internshipOffer.getListEmailInterestedStudents() != null)
                .flatMap(internshipOffer -> {
                    InternshipOfferStudentInterestViewDto internshipOfferDto = InternshipOfferMapper.toStudentInterestViewDto(internshipOffer);
                    return studentService.findAllByEmails(internshipOffer.getListEmailInterestedStudents())
                            .collectList()
                            .flatMap(students -> {
                                internshipOfferDto.setInterestedStudentList(students);
                                return Mono.just(internshipOfferDto);
                            });
                });
    }

    private Mono<InternshipOffer> addStudentEmailToOfferInterestedStudents(InternshipOffer internshipOffer,
                                                                           Student student) {
        return Mono.just(internshipOffer).flatMap(offer -> {
            if (offer.getIsExclusive()) {
                if (!student.getExclusiveOffersId().contains(offer.getId())) {
                    return Mono.error(
                            new UnauthorizedException("Student is not allowed to apply to this exclusive offer"));
                }
            }
            return Mono.just(offer);
        }).flatMap(offer -> {
            if (!offer.getListEmailInterestedStudents().contains(student.getEmail())) {
                offer.getListEmailInterestedStudents().add(student.getEmail());
                return studentService.addOfferToStudentAppliedOffers(student, offer.getId())
                        .then(internshipOfferRepository.save(offer));
            }
            return Mono.just(offer);
        });
    }


    public Flux<InternshipOffer> getAllNonValidatedOffers(Date sessionStart, Date sessionEnd) {
        return internshipOfferRepository.findAllByIsValidatedFalseAndLimitDateToApplyBetween(sessionStart, sessionEnd);
    }

    public Flux<InternshipOffer> getAllValidatedOffers(Date sessionStart, Date sessionEnd) {
        return internshipOfferRepository.findAllByIsValidatedTrueAndLimitDateToApplyBetween(sessionStart, sessionEnd);
    }

}
