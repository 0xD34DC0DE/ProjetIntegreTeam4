package com.team4.backend.service;

import com.team4.backend.InternshipManagerRegistration;
import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.model.*;
import com.team4.backend.pdf.InternshipContractPdfTemplate;
import com.team4.backend.repository.InternshipContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class InternshipContractService {

    private static final Logger log = LoggerFactory.getLogger(InternshipContractService.class);

    private final StudentService studentService;

    private final MonitorService monitorService;

    private final InternshipOfferService internshipOfferService;

    private final InternshipManagerService internshipManagerService;

    private final InternshipContractRepository internshipContractRepository;

    private final PdfService pdfService;

    public InternshipContractService(StudentService studentService,
                                     MonitorService monitorService,
                                     InternshipOfferService internshipOfferService,
                                     InternshipManagerService internshipManagerService,
                                     InternshipContractRepository internshipContractRepository,
                                     PdfService pdfService) {
        this.studentService = studentService;
        this.monitorService = monitorService;
        this.internshipOfferService = internshipOfferService;
        this.internshipManagerService = internshipManagerService;
        this.internshipContractRepository = internshipContractRepository;
        this.pdfService = pdfService;
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
                .onErrorMap(throwable -> {log.info(throwable.getLocalizedMessage()); return throwable;});
    }

    private Mono<InternshipContract> findContractByStudentEmail(String studentEmail) {
        return studentService.findByEmail(studentEmail)
                .flatMap(student ->
                        internshipContractRepository.findInternshipContractByStudentId(student.getId())
                );
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

            //TODO use the objects instead -> monitor.firstname , monitor.lastName
            Map<String, Object> variables = new HashMap<>();
            variables.put("internshipManager_firstName", internshipManager.getFirstName());
            variables.put("internshipManager_lastName", internshipManager.getLastName());
            variables.put("monitor_firstName", monitor.getFirstName());
            variables.put("monitor_lastName", monitor.getLastName());
            variables.put("student_firstName", student.getFirstName());
            variables.put("student_lastName", student.getLastName());
            return new InternshipContractPdfTemplate(variables);
        });
    }


}
