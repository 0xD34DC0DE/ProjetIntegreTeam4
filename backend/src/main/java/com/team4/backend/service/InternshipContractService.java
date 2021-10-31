package com.team4.backend.service;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.model.*;
import com.team4.backend.repository.InternshipContractRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class InternshipContractService {

    private final StudentService studentService;

    private final MonitorService monitorService;

    private final InternshipOfferService internshipOfferService;

    private final InternshipManagerService internshipManagerService;

    private final InternshipContractRepository internshipContractRepository;

    public InternshipContractService(StudentService studentService,
                                     MonitorService monitorService,
                                     InternshipOfferService internshipOfferService, InternshipManagerService internshipManagerService,
                                     InternshipContractRepository internshipContractRepository) {
        this.studentService = studentService;
        this.monitorService = monitorService;
        this.internshipOfferService = internshipOfferService;
        this.internshipManagerService = internshipManagerService;
        this.internshipContractRepository = internshipContractRepository;
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
                .startDate(internshipOffer.getBeginningDate())
                .endDate(internshipOffer.getEndingDate())
                .dailySchedule(internshipContractCreationDto.getDailySchedule())
                .hoursPerWeek(internshipContractCreationDto.getHoursPerWeek())
                .hourlyRate(internshipContractCreationDto.getHourlyRate())
                .internTasks(internshipOffer.getDescription())
                .studentSignature(studentSignature)
                .monitorSignature(monitorSignature)
                .internshipManagerSignature(internshipManagerSignature)
                .build();
    }


}
