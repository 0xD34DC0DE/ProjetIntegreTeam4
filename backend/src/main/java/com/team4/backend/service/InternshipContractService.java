package com.team4.backend.service;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.model.*;
import com.team4.backend.pdf.InternshipContractPdfTemplate;
import com.team4.backend.repository.InternshipContractRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class InternshipContractService {

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
        Mono<InternshipOffer> internshipOfferMono =
                internshipOfferService.findInternshipOfferById(internshipContractCreationDto.getInternshipOfferId());

        return internshipOfferMono.flatMap(internshipOffer ->
                Mono.zip(
                        internshipManagerService.findByEmail(internshipOffer.getEmailOfApprovingInternshipManager()),
                        monitorService.findByEmail(internshipOffer.getMonitorEmail()),
                        studentService.findByEmail(internshipContractCreationDto.getStudentEmail())
                )
                        .map(tuple -> buildInternshipContract(
                                tuple.getT1(),
                                tuple.getT2(),
                                tuple.getT3(),
                                internshipOffer,
                                internshipContractCreationDto)
                        )
                        .flatMap(internshipContractRepository::save));
    }


    private InternshipContract buildInternshipContract(InternshipManager internshipManager,
                                                       Monitor monitor,
                                                       Student student,
                                                       InternshipOffer internshipOffer,
                                                       InternshipContractCreationDto internshipContractCreationDto) {
        Signature monitorSignature = Signature.builder()
                .userId(monitor.getId())
                .hasSigned(true)
                .signDate(LocalDate.now())
                .build();

        Signature studentSignature = Signature.builder()
                .userId(student.getId())
                .hasSigned(false)
                .build();

        Signature internshipManagerSignature = Signature.builder()
                .userId(internshipManager.getId())
                .hasSigned(false)
                .build();

        return InternshipContract.builder()
                .address(internshipContractCreationDto.getAddress())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .dailySchedule(internshipContractCreationDto.getDailySchedule())
                .hoursPerWeek(internshipContractCreationDto.getHoursPerWeek())
                .hourlyRate(internshipContractCreationDto.getHourlyRate())
                .internTasks(internshipOffer.getDescription())
                .studentSignature(studentSignature)
                .monitorSignature(monitorSignature)
                .internshipManagerSignature(internshipManagerSignature)
                .build();
    }

    public Mono<byte[]> findContractByStudentEmail(String studentEmail) {
        return studentService.findByEmail(studentEmail)
                .flatMap(student -> internshipContractRepository.findInternshipContractByStudentId(student.getId()))
                .flatMap(this::getInternshipContractPdfTemplate)
                .flatMap(pdfService::renderPdf);
    }

    public Mono<InternshipContractPdfTemplate> getInternshipContractPdfTemplate(InternshipContract internshipContract) {
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
