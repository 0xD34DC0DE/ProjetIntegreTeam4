package com.team4.backend.service;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.dto.NotificationDto;
import com.team4.backend.exception.ContractNotFoundException;
import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.InternalServerErrorException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.model.*;
import com.team4.backend.model.enums.NotificationType;
import com.team4.backend.model.enums.Role;
import com.team4.backend.pdf.InternshipContractPdfTemplate;
import com.team4.backend.repository.InternshipContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InternshipContractService {

    private static final Logger log = LoggerFactory.getLogger(InternshipContractService.class);

    private final StudentService studentService;

    private final MonitorService monitorService;

    private final InternshipOfferService internshipOfferService;

    private final InternshipManagerService internshipManagerService;

    private final InternshipContractRepository internshipContractRepository;

    private final PdfService pdfService;

    private final UserService userService;

    private final NotificationService notificationService;

    public InternshipContractService(StudentService studentService,
                                     MonitorService monitorService,
                                     InternshipOfferService internshipOfferService,
                                     InternshipManagerService internshipManagerService,
                                     InternshipContractRepository internshipContractRepository,
                                     PdfService pdfService,
                                     UserService userService, NotificationService notificationService) {
        this.studentService = studentService;
        this.monitorService = monitorService;
        this.internshipOfferService = internshipOfferService;
        this.internshipManagerService = internshipManagerService;
        this.internshipContractRepository = internshipContractRepository;
        this.pdfService = pdfService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public Mono<InternshipContract> signContract(InternshipContractDto internshipContractDto, String userEmail) {
        return Mono.zip(
                userService.findByEmail(userEmail),
                findInternshipContractById(internshipContractDto.getContractId())
        ).flatMap(tuple -> {
            User user = tuple.getT1();
            InternshipContract contract = tuple.getT2();
            return signContractByUser(contract, user);
        }).flatMap(internshipContractRepository::save);
    }

    private Mono<InternshipContract> signContractByUser(InternshipContract contract, User user) {
        return getSignatureByRole(contract, user.getRole())
                .switchIfEmpty(
                        Mono.error(
                                new ForbiddenActionException("User with role: " +
                                        user.getRole().toString() + " cannot sign contracts.")
                        )
                ).flatMap(signature -> {

                    if (!signature.getUserId().equals(user.getId())) {
                        return Mono.error(
                                new UnauthorizedException("Id of user with role: " + user.getRole() +
                                        " and id: " + user.getId() + " did not match, id of user in contract")
                        );
                    }

                    signature.setHasSigned(true);
                    signature.setSignDate(LocalDate.now());

                    switch (user.getRole()) {
                        case STUDENT:
                            contract.setStudentSignature(signature);
                            return Mono.just(contract);
                        case INTERNSHIP_MANAGER:
                            contract.setInternshipManagerSignature(signature);
                            return Mono.just(contract);
                        default:
                            return Mono.error(
                                    new InternalServerErrorException(
                                            "Unexpected error: default case reached for role switch statement"
                                    )
                            );
                    }
                });
    }

    private Mono<Signature> getSignatureByRole(InternshipContract contract, Role role) {
        switch (role) {
            case STUDENT:
                return Mono.just(contract.getStudentSignature());
            case INTERNSHIP_MANAGER:
                return Mono.just(contract.getInternshipManagerSignature());
            default:
                return Mono.empty();
        }
    }

    private Mono<InternshipContract> findInternshipContractById(String contractId) {
        return internshipContractRepository.findById(contractId)
                .switchIfEmpty(
                        Mono.error(
                                new ContractNotFoundException(
                                        "Could not find internship contract with id: " + contractId
                                )
                        )
                );
    }

    public Mono<InternshipContract> initiateContract(InternshipContractCreationDto internshipContractCreationDto) {
        return buildInternshipContractFromInternshipContractCreationDto(internshipContractCreationDto)
                .flatMap(internshipContractRepository::save)
                .flatMap(this::sendContractCreationNotifications);
    }

    private Mono<InternshipContract> sendContractCreationNotifications(InternshipContract internshipContract) {
        return internshipOfferService.findInternshipOfferById(internshipContract.getInternshipOfferId())
                .flatMap(internshipOffer -> {
                    Signature studentSignature = internshipContract.getStudentSignature();
                    Signature internshipManagerSignature = internshipContract.getInternshipManagerSignature();
                    String companyName = internshipOffer.getCompanyName();

                    Set<String> userIds = new HashSet<>() {{
                        add(studentSignature.getUserId());
                        add(internshipManagerSignature.getUserId());
                    }};

                    NotificationDto usersNotificationDto = getNotificationDto(
                            internshipContract.getId(),
                            userIds,
                            companyName);

                    return notificationService.createNotification(usersNotificationDto)
                            .map(unused -> internshipContract);
                });
    }

    private NotificationDto getNotificationDto(String internshipContractId, Set<String> userIds, String companyName) {
        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("contractId", internshipContractId);

        return NotificationDto.notificationDtoBuilder()
                .title("Signature de contrat")
                .content("Signature du contrat pour: " + companyName)
                .receiverIds(userIds)
                .seenIds(Set.of())
                .data(notificationData)
                .notificationType(NotificationType.SIGN_CONTRACT)
                .build();
    }

    public Mono<byte[]> getContract(String internshipOfferId, String studentEmail) {

        InternshipContractDto insternshipContractDto = InternshipContractDto.builder()
                .internshipOfferId(internshipOfferId)
                .studentEmail(studentEmail)
                .build();

        return studentService.findByEmail(studentEmail)
                .flatMap(student -> internshipContractRepository.findInternshipContractByStudentId(student.getId()))
                .switchIfEmpty(buildInternshipContractFromInternshipContractDto(insternshipContractDto))
                .flatMap(this::getPdfBytes)
                .onErrorMap(throwable -> {
                    log.info(throwable.getLocalizedMessage());
                    return throwable;
                });
    }

    public Mono<byte[]> getContractById(String contractId, String userEmail) {
        return internshipContractRepository.findById(contractId)
                .flatMap(internshipContract -> verifyUserIsInContract(internshipContract, userEmail))
                .flatMap(this::getPdfBytes)
                .onErrorMap(throwable -> {
                    log.info(throwable.getLocalizedMessage());
                    return throwable;
                });
    }

    private Mono<InternshipContract> verifyUserIsInContract(InternshipContract internshipContract, String userEmail) {
        return userService.findByEmail(userEmail)
                .map(User::getId)
                .flatMap(userId -> {
                    if (!internshipContract.isUserInContract(userId)) {
                        return Mono.error(
                                new UnauthorizedException(
                                        "User with email: " + userEmail + " was not found inside contract"
                                )
                        );
                    }

                    return Mono.just(internshipContract);
                });
    }

    private Mono<byte[]> getPdfBytes(InternshipContract internshipContract) {
        return Mono.just(internshipContract)
                .flatMap(this::getInternshipContractPdfTemplate)
                .flatMap(pdfService::renderPdf);
    }

    private Mono<Tuple4<InternshipManager, Monitor, Student, InternshipOffer>> getContractObjectsTuple(
            InternshipContractDto internshipContractDto) {

        Mono<InternshipOffer> internshipOfferMono =
                internshipOfferService.findInternshipOfferById(internshipContractDto.getInternshipOfferId());

        return internshipOfferMono.flatMap(internshipOffer ->
                Mono.zip(
                        internshipManagerService.findByEmail(internshipOffer.getEmailOfApprovingInternshipManager()),
                        monitorService.findByEmail(internshipOffer.getMonitorEmail()),
                        studentService.findByEmail(internshipContractDto.getStudentEmail()),
                        Mono.just(internshipOffer)
                )
        );
    }

    private Mono<InternshipContract> buildInternshipContractFromInternshipContractDto(
            InternshipContractDto internshipContractDto) {

        return getContractObjectsTuple(internshipContractDto)
                .map(tuple -> buildInternshipContract(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3(),
                        tuple.getT4())
                );

    }

    private Mono<InternshipContract> buildInternshipContractFromInternshipContractCreationDto(
            InternshipContractCreationDto internshipContractCreationDto) {

        return getContractObjectsTuple(internshipContractCreationDto)
                .map(tuple -> buildSignedInternshipContract(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3(),
                        tuple.getT4(),
                        internshipContractCreationDto)
                );
    }

    private InternshipContract buildInternshipContract(InternshipManager internshipManager,
                                                       Monitor monitor,
                                                       Student student,
                                                       InternshipOffer internshipOffer) {

        Signature monitorSignature = buildSignature(monitor.getId(), false);
        Signature studentSignature = buildSignature(student.getId(), false);
        Signature internshipManagerSignature = buildSignature(internshipManager.getId(), false);

        return InternshipContract.builder()
                .internshipOfferId(internshipOffer.getId())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .internTasks(internshipOffer.getDescription())
                .studentSignature(studentSignature)
                .monitorSignature(monitorSignature)
                .internshipManagerSignature(internshipManagerSignature)
                .build();
    }

    private Signature buildSignature(String id, boolean isSigned) {
        return Signature.builder()
                .userId(id)
                .hasSigned(isSigned)
                .signDate(isSigned ? LocalDate.now() : null)
                .build();
    }

    private InternshipContract buildSignedInternshipContract(
            InternshipManager internshipManager,
            Monitor monitor,
            Student student,
            InternshipOffer internshipOffer,
            InternshipContractCreationDto internshipContractCreationDto) {

        Signature monitorSignature = buildSignature(monitor.getId(), true);

        InternshipContract internshipContract = buildInternshipContract(internshipManager,
                monitor,
                student,
                internshipOffer);

        internshipContract.setAddress(internshipContractCreationDto.getAddress());
        internshipContract.setDailySchedule(internshipContractCreationDto.getDailySchedule());
        internshipContract.setHoursPerWeek(internshipContractCreationDto.getHoursPerWeek());
        internshipContract.setHourlyRate(internshipContractCreationDto.getHourlyRate());

        internshipContract.setMonitorSignature(monitorSignature);

        return internshipContract;
    }

    private Mono<InternshipContractPdfTemplate> getInternshipContractPdfTemplate(InternshipContract internshipContract) {
        return Mono.zip(
                internshipManagerService.findById(internshipContract.getInternshipManagerSignature().getUserId()),
                monitorService.findById(internshipContract.getMonitorSignature().getUserId()),
                studentService.findById(internshipContract.getStudentSignature().getUserId())
        ).map(tuple -> {
            InternshipManager internshipManager = tuple.getT1();
            Monitor monitor = tuple.getT2();
            Student student = tuple.getT3();
            Long weeks = ChronoUnit.WEEKS.between(
                    internshipContract.getBeginningDate(),
                    internshipContract.getEndingDate()
            );

            Map<String, Object> variables = new HashMap<>();
            variables.put("internshipManager", internshipManager);
            variables.put("monitor", monitor);
            variables.put("student", student);
            variables.put("contract", internshipContract);
            variables.put("weeks", weeks);
            variables.put("student_signature_name", getSignatureName(internshipContract.getStudentSignature(), student));
            variables.put("monitor_signature_name", getSignatureName(internshipContract.getMonitorSignature(), monitor));
            variables.put("internshipManager_signature_name", getSignatureName(
                    internshipContract.getInternshipManagerSignature(),
                    internshipManager)
            );
            return new InternshipContractPdfTemplate(variables);
        });
    }

    private String getSignatureName(Signature signature, User user) {
        if (signature.getHasSigned()) {
            return user.getFirstName() + " " + user.getLastName();
        } else {
            return "";
        }
    }

    // TODO Make this method more generic, not just for the monitor
    // Aka take a contract id instead of this mess
    public Mono<Boolean> hasSigned(String internshipOfferId, String studentEmail, String userEmail) {

        // Using an InternshipContractDto to avoid having to make an override
        // of the method to accept an internshipOfferId
        InternshipContractDto internshipContractDto = InternshipContractDto.builder()
                .internshipOfferId(internshipOfferId)
                .studentEmail(studentEmail)
                .build();

        return userService.findByEmail(userEmail)
                .flatMap(user ->
                        getContractObjectsTuple(internshipContractDto)
                                .flatMap(tuple -> {
                                    InternshipManager internshipManager = tuple.getT1();
                                    Monitor monitor = tuple.getT2();
                                    Student student = tuple.getT3();

                                    return internshipContractRepository.hasSigned(
                                            internshipOfferId,
                                            internshipManager.getId(),
                                            student.getId(),
                                            monitor.getId(),
                                            user.getId());
                                })
                );
    }

    public Mono<Boolean> hasSignedByContractId(String contractId, String userEmail) {
        return Mono.zip(
                userService.findByEmail(userEmail),
                internshipContractRepository.findById(contractId)
        ).map(tuple -> tuple.getT2().hasUserSigned(tuple.getT1().getId()));
    }

    public void notifyMonitorsTwoWeeksLeft() {
        internshipContractRepository.findAll()
                .filter(internshipContract -> LocalDate.now().until(internshipContract.getEndingDate()).getDays() == 14)
                .collectList()
                .flatMap(internshipContracts -> {
                            List<String> monitorList = List.copyOf(internshipContracts.stream()
                                    .map(internshipContract -> internshipContract.getMonitorSignature().getUserId())
                                    .collect(Collectors.toList()));

                            Flux<Monitor> monitorFlux = monitorService.findAllByIds(monitorList)
                                    .flatMap(monitor -> {
                                        int frequency = Collections.frequency(monitorList, monitor.getId());
                                        return Flux.fromIterable(Collections.nCopies(frequency, monitor));
                                    });

                            List<String> studentList = List.copyOf(internshipContracts.stream()
                                    .map(internshipContract -> internshipContract.getStudentSignature().getUserId())
                                    .collect(Collectors.toList()));

                            Flux<Student> studentFlux = studentService.findAllByIds(studentList);

                            return Flux.zip(
                                            monitorFlux,
                                            studentFlux
                                    )
                                    .delayElements(Duration.ofSeconds(1))
                                    .flatMap(tuple -> {
                                        Monitor monitor = tuple.getT1();
                                        Student student = tuple.getT2();
                                        return createTwoWeeksNoticeNotification(monitor, student);
                                    })
                                    .collectList();
                        }
                )
                .subscribe();
    }

    public Mono<Notification> createTwoWeeksNoticeNotification(Monitor monitor, Student student) {
        NotificationDto notificationDto = NotificationDto.notificationDtoBuilder()
                .id(null)
                .creationDate(null)
                .title("Avis de fin de stage")
                .content("Le stage de l'étudiant " + student.getFirstName() + " " + student.getLastName() + " se termine dans deux semaines (" + LocalDate.now() + ")")
                .receiverIds(Set.of(monitor.getId()))
                .build();
        return notificationService.createNotification(notificationDto);
    }

}
