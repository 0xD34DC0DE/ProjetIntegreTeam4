package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.dto.NotificationDto;
import com.team4.backend.exception.InternshipOfferNotFoundException;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.*;
import com.team4.backend.model.enums.NotificationType;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.util.ValidatingPageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    private final MonitorService monitorService;

    private final StudentService studentService;

    private final SemesterService semesterService;

    private final NotificationService notificationService;

    private final UserService userService;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository,
                                  MonitorService monitorService,
                                  StudentService studentService,
                                  SemesterService semesterService, NotificationService notificationService, UserService userService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.monitorService = monitorService;
        this.studentService = studentService;
        this.semesterService = semesterService;
        this.notificationService = notificationService;
        this.userService = userService;
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

    public Mono<Notification> createNewInternshipNotification() {
        return userService
                .getAll("STUDENT")
                .map(User::getId)
                .collect(Collectors.toSet())
                .flatMap(studentsId -> notificationService
                        .createNotification(
                                NotificationDto.notificationDtoBuilder()
                                        .receiverIds(studentsId)
                                        .data(null)
                                        .seenIds(Set.of())
                                        .title("Nouvelle offre de stage")
                                        .content("Appuyez pour visualiser la nouvelle offre")
                                        .notificationType(NotificationType.NEW_INTERNSHIP_OFFER)
                                        .build()
                        ));
    }


    public Flux<InternshipOfferStudentViewDto> getStudentExclusiveOffers(String studentEmail,
                                                                         Integer page,
                                                                         Integer size) {
        return Mono.zip(studentService.findByEmail(studentEmail), semesterService.getCurrentSemester())
                .flatMapMany(tuple -> {
                    Student student = tuple.getT1();
                    Semester semester = tuple.getT2();

                    return ValidatingPageRequest
                            .applyPaging(student.getExclusiveOffersId(), page, size)
                            .flatMap(offerId -> internshipOfferRepository.
                                    findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(offerId, semester.getFrom(), semester.getTo()))
                            .map(InternshipOfferMapper::toStudentViewDto).map(internshipOfferDto -> {
                                if (student.getAppliedOffersId().contains(internshipOfferDto.getId()))
                                    internshipOfferDto.setHasAlreadyApplied(true);
                                return internshipOfferDto;
                            });

                });
    }

    public Flux<InternshipOfferStudentViewDto> getGeneralInternshipOffers(Integer page,
                                                                          Integer size,
                                                                          String studentEmail) {
        return Mono.zip(studentService.findByEmail(studentEmail), semesterService.getCurrentSemester())
                .flatMapMany(tuple -> {
                    Student student = tuple.getT1();
                    Semester semester = tuple.getT2();

                    return ValidatingPageRequest.getPageRequestMono(page, size)
                            .flatMapMany(pageRequest -> internshipOfferRepository.findAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                                    semester.getFrom(),
                                    semester.getTo(),
                                    pageRequest
                            ))
                            .map(InternshipOfferMapper::toStudentViewDto).flatMap(internshipOfferDto -> {
                                if (student.getAppliedOffersId().contains(internshipOfferDto.getId()))
                                    internshipOfferDto.setHasAlreadyApplied(true);
                                return Mono.just(internshipOfferDto);
                            });
                });
    }

    public Flux<InternshipOffer> getNotYetValidatedInternshipOffers(String semesterFullName) {
        return semesterService.findByFullName(semesterFullName)
                .flatMapMany(semester -> internshipOfferRepository
                        .findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(semester.getFrom(), semester.getTo()));
    }

    public Mono<InternshipOffer> validateInternshipOffer(String id, Boolean isValid) {
        return findInternshipOfferById(id).map(offer -> {
                    offer.setIsValidated(isValid);
                    offer.setValidationDate(LocalDateTime.now());

                    return offer;
                })
                .flatMap(internshipOfferRepository::save)
                .doOnSuccess(internshipOffer -> {
                    createNewInternshipNotification().subscribe();
                    createInternshipOfferValidationNotification(internshipOffer.getMonitorEmail(), internshipOffer.getIsValidated()).subscribe();
                });
    }

    public Mono<Notification> createInternshipOfferValidationNotification(String monitorEmail, boolean isValid) {
        return userService
                .findByEmail(monitorEmail)
                .flatMap(monitor ->
                        notificationService.createNotification(
                                NotificationDto.notificationDtoBuilder()
                                        .receiverIds(Set.of(monitor.getId()))
                                        .data(null)
                                        .seenIds(Set.of())
                                        .title("Votre offre a été " + (isValid ? "acceptée" : "refusée"))
                                        .content(isValid ? "Les étudiants peuvent maintenant appliquer à votre offre" : "Contacter le gestionnaire de stage pour plus de détails")
                                        .notificationType(null)
                                        .build()
                        )
                );
    }

    public Mono<Long> getInternshipOffersPageCount(Integer size) {
        if (size < 1) {
            return Mono.error(InvalidPageRequestException::new);
        }

        return semesterService.getCurrentSemester().flatMap(semester ->
                internshipOfferRepository.countAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(semester.getFrom(), semester.getTo())
                        .map(count -> (long) Math.ceil((double) count / (double) size))
        );
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

    public Flux<InternshipOfferStudentInterestViewDto> getInterestedStudents(String monitorEmail, String semesterFullName) {

        return semesterService.findByFullName(semesterFullName)
                .flatMapMany(semester ->
                        internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                                        monitorEmail,
                                        semester.getFrom(),
                                        semester.getTo()
                                ).filter(internshipOffer -> !internshipOffer.getListEmailInterestedStudents().isEmpty())
                                .flatMap(internshipOffer -> {
                                    InternshipOfferStudentInterestViewDto internshipOfferDto = InternshipOfferMapper.toStudentInterestViewDto(internshipOffer);

                                    return studentService.findAllByEmails(internshipOffer.getListEmailInterestedStudents())
                                            .collectList()
                                            .flatMap(students -> {
                                                internshipOfferDto.setInterestedStudentList(students);
                                                return Mono.just(internshipOfferDto);
                                            });
                                })
                );
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

    public Flux<InternshipOffer> getAllNonValidatedOffers(String semesterFullName) {

        return semesterService.findByFullName(semesterFullName)
                .flatMapMany(semester ->
                        internshipOfferRepository.findAllByIsValidatedFalseAndLimitDateToApplyIsBetween(semester.getFrom(), semester.getTo())
                );
    }

    public Flux<InternshipOffer> getAllValidatedOffers(String semesterFullName) {

        return semesterService.findByFullName(semesterFullName)
                .flatMapMany(semester ->
                        internshipOfferRepository.findAllByIsValidatedTrueAndLimitDateToApplyIsBetween(semester.getFrom(), semester.getTo())
                );
    }

}
