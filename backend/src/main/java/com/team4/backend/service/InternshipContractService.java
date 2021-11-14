package com.team4.backend.service;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.mapping.NotificationMapper;
import com.team4.backend.model.*;
import com.team4.backend.model.enums.NotificationSeverity;
import com.team4.backend.pdf.InternshipContractPdfTemplate;
import com.team4.backend.repository.InternshipContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Mono<InternshipContract> initiateContract(InternshipContractCreationDto internshipContractCreationDto) {
        return buildInternshipContractFromInternshipContractCreationDto(internshipContractCreationDto)
                .flatMap(internshipContractRepository::save);
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

            //TODO use the objects instead -> monitor.firstname , monitor.lastName
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

    public void getInternshipContractsTwoWeeksLeft() {
        internshipContractRepository.findAll()
                .filter(internshipContract -> LocalDate.now().until(internshipContract.getEndingDate()).getDays() == 14)
                .collectList()
                .flatMap(internshipContracts -> {
                            List<String> monitorList = List.copyOf(internshipContracts.stream()
                                    .map(internshipContract -> internshipContract.getMonitorSignature().getUserId()).collect(Collectors.toList()));

                            Flux<Monitor> monitorFlux = monitorService.findAllByIds(monitorList);

                            List<String> studentList = List.copyOf(internshipContracts.stream()
                                    .map(internshipContract -> internshipContract.getStudentSignature().getUserId()).collect(Collectors.toList()));

                            Flux<Student> studentFlux = studentService.findAllByIds(studentList);

                            return Flux.zip(
                                            monitorFlux,
                                            studentFlux
                                    )
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
        Notification notification = Notification.notificationBuilder()
                .title("Avis de fin de stage")
                .content("Le stage de l'étudiant " + student.getFirstName() + " " + student.getLastName() + " se termine dans deux semaines")
                .severity(NotificationSeverity.HIGH)
                .receiverId(monitor.getId())
                .build();
        return notificationService.createNotification(NotificationMapper.toDto(notification));
    }

}
