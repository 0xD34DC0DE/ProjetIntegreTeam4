package com.team4.backend.service;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.PdfGenerationErrorException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.model.*;
import com.team4.backend.model.enums.Role;
import com.team4.backend.repository.InternshipContractRepository;
import com.team4.backend.testdata.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InternshipContractServiceTest {

    @Mock
    InternshipContractRepository internshipContractRepository;

    @Mock
    StudentService studentService;

    @Mock
    MonitorService monitorService;

    @Mock
    InternshipOfferService internshipOfferService;

    @Mock
    InternshipManagerService internshipManagerService;

    @Mock
    PdfService pdfService;

    @Mock
    UserService userService;

    @Mock
    NotificationService notificationService;

    @Mock
    SpringTemplateEngine templateEngine;

    @InjectMocks
    InternshipContractService internshipContractService;

    //ARRANGE

    //ACT

    //ASSERT

    @Test
    void shouldSignContractStudent() {
        //ARRANGE
        User user = UserMockData.getUser();
        Signature signature = Signature.builder()
                .hasSigned(false)
                .signDate(null)
                .userId(user.getId())
                .build();
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();
        internshipContract.setStudentSignature(signature);

        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(userService.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(internshipContract));
        when(internshipContractRepository.save(any())).thenReturn(Mono.just(internshipContract));

        //ACT
        Mono<InternshipContract> internshipContractMono =
                internshipContractService.signContract(internshipContractDto, user.getEmail());

        //ASSERT
        StepVerifier.create(internshipContractMono)
                .assertNext(result -> {
                    assertTrue(result.getStudentSignature().getHasSigned());
                    assertNotNull(result.getStudentSignature().getSignDate());
                }).verifyComplete();
    }

    @Test
    void shouldSignContractInternshipManager() {
        //ARRANGE
        User user = UserMockData.getUser();
        user.setRole(Role.INTERNSHIP_MANAGER);
        Signature signature = Signature.builder()
                .hasSigned(false)
                .signDate(null)
                .userId(user.getId())
                .build();
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();
        internshipContract.setInternshipManagerSignature(signature);

        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(userService.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(internshipContract));
        when(internshipContractRepository.save(any())).thenReturn(Mono.just(internshipContract));

        //ACT
        Mono<InternshipContract> internshipContractMono =
                internshipContractService.signContract(internshipContractDto, user.getEmail());

        //ASSERT
        StepVerifier.create(internshipContractMono)
                .assertNext(result -> {
                    assertTrue(result.getInternshipManagerSignature().getHasSigned());
                    assertNotNull(result.getInternshipManagerSignature().getSignDate());
                }).verifyComplete();
    }

    @Test
    void shouldNotSignContractWhenRoleIsMonitor() {
        //ARRANGE
        User user = UserMockData.getUser();
        user.setRole(Role.MONITOR);
        Signature signature = Signature.builder()
                .hasSigned(false)
                .signDate(null)
                .userId(user.getId())
                .build();
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();
        internshipContract.setMonitorSignature(signature);

        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(userService.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(internshipContract));

        //ACT
        Mono<InternshipContract> internshipContractMono =
                internshipContractService.signContract(internshipContractDto, user.getEmail());

        //ASSERT
        StepVerifier.create(internshipContractMono)
                .expectError(ForbiddenActionException.class)
                .verify();
    }

    @Test
    void shouldNotSignContractWhenUserNotInContract() {
        //ARRANGE
        User user = UserMockData.getUser();
        Signature signature = Signature.builder()
                .hasSigned(false)
                .signDate(null)
                .userId("wrongId")
                .build();
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();
        internshipContract.setStudentSignature(signature);

        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        when(userService.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(internshipContract));

        //ACT
        Mono<InternshipContract> internshipContractMono =
                internshipContractService.signContract(internshipContractDto, user.getEmail());

        //ASSERT
        StepVerifier.create(internshipContractMono)
                .expectError(UnauthorizedException.class)
                .verify();
    }

    @Test
    void shouldInitiateContract() {
        //ARRANGE
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();

        Monitor monitor = MonitorMockData.getMockMonitor();
        Student student = StudentMockData.getMockStudent();
        InternshipManager internshipManager = InternshipManagerMockData.getInternshipManager();

        InternshipContractCreationDto internshipContractCreationDto =
                InternshipContractMockData.getInternshipContractCreationDto();
        internshipContractCreationDto.setStudentEmail(student.getEmail());

        Signature signature = Signature.builder()
                .hasSigned(false)
                .signDate(null)
                .userId(monitor.getId())
                .build();
        internshipContract.setMonitorSignature(signature);

        InternshipOffer internshipOffer = InternshipOfferMockData.getFirstInternshipOffer();
        internshipOffer.setEmailOfApprovingInternshipManager(internshipManager.getEmail());
        internshipOffer.setMonitorEmail(monitor.getEmail());

        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();
        internshipContractDto.setStudentEmail(student.getEmail());

        when(internshipContractRepository.save(any())).thenAnswer(
                (InvocationOnMock invocation) -> Mono.just(invocation.getArguments()[0])
        );
        when(internshipOfferService.findInternshipOfferById(anyString())).thenReturn(Mono.just(internshipOffer));

        when(monitorService.findByEmail(eq(monitor.getEmail())))
                .thenReturn(Mono.just(monitor));
        when(studentService.findByEmail(eq(student.getEmail())))
                .thenReturn(Mono.just(student));
        when(internshipManagerService.findByEmail(eq(internshipManager.getEmail())))
                .thenReturn(Mono.just(internshipManager));

        when(notificationService.createNotification(any())).thenReturn(Mono.just(Notification.builder().build()));

        //ACT
        Mono<InternshipContract> internshipContractMono =
                internshipContractService.initiateContract(internshipContractCreationDto);

        //ASSERT
        StepVerifier.create(internshipContractMono)
                .assertNext(result -> {
                    assertTrue(result.getMonitorSignature().getHasSigned());
                    assertNotNull(result.getMonitorSignature().getSignDate());
                }).verifyComplete();
    }

    @Test
    void shouldGetContractWhenContractExists() {
        //ARRANGE
        when(internshipContractRepository.findInternshipContractByStudentId(anyString()))
                .thenReturn(Mono.just(InternshipContractMockData.getInternshipContract()));

        when(internshipOfferService.findInternshipOfferById(anyString()))
                .thenReturn(Mono.just(InternshipOfferMockData.getInternshipOffer()));

        when(internshipManagerService.findById(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findById(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findById(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(pdfService.renderPdf(any())).thenReturn(Mono.just(new byte[]{1, 2, 3}));
        //ACT
        Mono<byte[]> byteMono =
                internshipContractService.getContract("offerId", "student@email.com");

        //ASSERT
        StepVerifier.create(byteMono)
                .assertNext(bytes ->
                    assertTrue(bytes.length > 0)
                ).verifyComplete();
    }

    @Test
    void shouldGetContractWhenContractDoesNotExists() {
        //ARRANGE
        when(internshipContractRepository.findInternshipContractByStudentId(anyString()))
                .thenReturn(Mono.empty());

        when(internshipOfferService.findInternshipOfferById(anyString()))
                .thenReturn(Mono.just(InternshipOfferMockData.getInternshipOffer()));

        when(internshipManagerService.findById(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findById(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findById(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(internshipManagerService.findByEmail(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findByEmail(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(pdfService.renderPdf(any())).thenReturn(Mono.just(new byte[]{1, 2, 3}));
        //ACT
        Mono<byte[]> byteMono =
                internshipContractService.getContract("offerId", "student@email.com");

        //ASSERT
        StepVerifier.create(byteMono)
                .assertNext(bytes ->
                    assertTrue(bytes.length > 0)
                ).verifyComplete();
    }

    @Test
    void shouldHandleContractPdfError() {
        //ARRANGE
        when(internshipContractRepository.findInternshipContractByStudentId(anyString()))
                .thenReturn(Mono.empty());

        when(internshipOfferService.findInternshipOfferById(anyString()))
                .thenReturn(Mono.just(InternshipOfferMockData.getInternshipOffer()));

        when(internshipManagerService.findById(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findById(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findById(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(internshipManagerService.findByEmail(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findByEmail(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        when(pdfService.renderPdf(any())).thenReturn(Mono.error(PdfGenerationErrorException::new));
        //ACT
        Mono<byte[]> byteMono =
                internshipContractService.getContract("offerId", "student@email.com");

        //ASSERT
        StepVerifier.create(byteMono)
                .expectError(PdfGenerationErrorException.class)
                .verify();
    }

    @Test
    void hasSigned() {
        //ARRANGE
        when(userService.findByEmail(anyString())).thenReturn(Mono.just(UserMockData.getUser()));
        when(internshipContractRepository.hasSigned(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(true));
        when(internshipOfferService.findInternshipOfferById(anyString()))
                .thenReturn(Mono.just(InternshipOfferMockData.getInternshipOffer()));
        when(internshipManagerService.findByEmail(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findByEmail(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findByEmail(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));
        //ACT
        Mono<Boolean> booleanMono = internshipContractService
                .hasSigned("internshipOfferId", "studenEmail", "userEmail");

        //ASSERT
        StepVerifier.create(booleanMono)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void hasSignedByContractId() {
        //ARRANGE
        when(userService.findByEmail(anyString())).thenReturn(Mono.just(UserMockData.getUser()));
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(InternshipContractMockData.getInternshipContract()));
        //ACT
        Mono<Boolean> booleanMono =
                internshipContractService.hasSignedByContractId("contractId", "userEmail");

        //ASSERT
        StepVerifier.create(booleanMono)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void shouldGetContractById() {
        //ARRANGE
        when(internshipContractRepository.findById(anyString()))
                .thenReturn(Mono.just(InternshipContractMockData.getInternshipContract()));
        when(pdfService.renderPdf(any())).thenReturn(Mono.just(new byte[]{1, 2, 3}));


        when(userService.findByEmail(anyString())).thenReturn(Mono.just(UserMockData.getUser()));

        when(internshipManagerService.findById(anyString()))
                .thenReturn(Mono.just(InternshipManagerMockData.getInternshipManager()));
        when(monitorService.findById(anyString())).thenReturn(Mono.just(MonitorMockData.getMockMonitor()));
        when(studentService.findById(anyString())).thenReturn(Mono.just(StudentMockData.getMockStudent()));

        //ACT
        Mono<byte[]> byteMono =
                internshipContractService.getContractById("contractId", "student@email.com");

        //ASSERT
        StepVerifier.create(byteMono)
                .assertNext(bytes ->
                    assertTrue(bytes.length > 0)
                ).verifyComplete();
    }

    @Test
    void shouldNotifyMonitorsWhenInternshipHas2WeeksLeft() {
        //ARRANGE
        List<InternshipContract> internshipContracts = new ArrayList<>();

        InternshipContract internshipContract1 = InternshipContractMockData.getInternshipContract();
        internshipContract1.setEndingDate(LocalDate.now().plusWeeks(2));
        internshipContract1.setStudentSignature(Signature.builder().userId("mock_id").build());
        internshipContracts.add(internshipContract1);

        InternshipContract internshipContract2 = InternshipContractMockData.getInternshipContract();
        internshipContract2.setEndingDate(LocalDate.now().plusWeeks(2));
        internshipContract2.setStudentSignature(Signature.builder().userId("000111222abc").build());
        internshipContracts.add(internshipContract2);

        when(internshipContractRepository.findAll()).thenReturn(Flux.fromIterable(internshipContracts));
        when(monitorService.findAllByIds(anyList()))
                .thenReturn(
                        Flux.fromIterable(
                                List.of(
                                        MonitorMockData.getMockMonitor(),
                                        MonitorMockData.getMockMonitor()
                                )
                        )
                );
        when(studentService.findAllByIds(anyList()))
                .thenReturn(
                        Flux.fromIterable(
                                List.of(
                                        StudentMockData.getMockStudent(),
                                        StudentMockData.getMockSecondStudent()
                                )
                        )
                );

        when(notificationService.createNotification(any()))
                .thenReturn(Mono.just(NotificationMockData.getNotification()));

        InternshipContractService internshipContractService1 =
                spy(
                        new InternshipContractService(
                                studentService,
                                monitorService,
                                internshipOfferService,
                                internshipManagerService,
                                internshipContractRepository,
                                pdfService,
                                userService,
                                notificationService)
                );

        //ACT
        internshipContractService1.notifyMonitorsTwoWeeksLeft();

        //ASSERT
        verify(internshipContractService1, timeout(2500).times(2))
                .createTwoWeeksNoticeNotification(
                        any(),
                        any()
                );
    }

}