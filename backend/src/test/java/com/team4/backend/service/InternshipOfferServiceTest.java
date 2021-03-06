package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.*;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Notification;
import com.team4.backend.model.Semester;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.SemesterName;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.testdata.InternshipOfferMockData;
import com.team4.backend.testdata.MonitorMockData;
import com.team4.backend.testdata.SemesterMockData;
import com.team4.backend.testdata.StudentMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@Log
@ExtendWith(MockitoExtension.class)
public class InternshipOfferServiceTest {

    @Mock
    InternshipOfferRepository internshipOfferRepository;

    @Mock
    MonitorService monitorService;

    @Mock
    StudentService studentService;

    @Mock
    SemesterService semesterService;

    @InjectMocks
    InternshipOfferService internshipOfferService;

    @Test
    void shouldCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferCreationDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getMonitorEmail()))
                .thenReturn(Mono.just(true));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService
                .addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer).assertNext(Assertions::assertNotNull).verifyComplete();
    }

    @Test
    void shouldNotCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferCreationDto();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getMonitorEmail()))
                .thenReturn(Mono.just(false));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService
                .addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer).expectError(UserNotFoundException.class).verify();
    }

    @Test
    void shouldGetExclusiveInternshipOfferStudentViews() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<String> exclusiveOfferIds = new ArrayList<>(student.getExclusiveOffersId());

        InternshipOffer internshipOffer1 = InternshipOfferMockData.getInternshipOffer();
        internshipOffer1.setId(exclusiveOfferIds.get(0));

        InternshipOffer internshipOffer2 = InternshipOfferMockData.getInternshipOffer();
        internshipOffer2.setId(exclusiveOfferIds.get(1));

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                same(exclusiveOfferIds.get(0)), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Mono.just(internshipOffer1));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                same(exclusiveOfferIds.get(1)), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Mono.just(internshipOffer2));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getStudentExclusiveOffers(student.getEmail(), 0, 2);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(offer -> assertEquals(offer.getId(), exclusiveOfferIds.get(0)))
                .assertNext(offer -> assertEquals(offer.getId(), exclusiveOfferIds.get(1)))
                .verifyComplete();
    }

    @Test
    void shouldGetExclusiveInternshipOfferStudentViewsWithinPage() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<String> exclusiveOfferIds = new ArrayList<>(student.getExclusiveOffersId());

        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();
        internshipOffer.setId(exclusiveOfferIds.get(1));

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                same(exclusiveOfferIds.get(1)), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Mono.just(internshipOffer));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getStudentExclusiveOffers(student.getEmail(), 1, 1);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(offer -> assertEquals(offer.getId(), exclusiveOfferIds.get(1)))
                .verifyComplete();
    }

    @Test
    void shouldNotGetExclusiveInternshipOfferStudentViewsInvalidEmail() {
        //ARRANGE
        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getStudentExclusiveOffers("invalid@gmail.com", 0, 1);

        //ASSERT
        StepVerifier.create(internshipOfferFlux).expectError(UserNotFoundException.class).verify();
    }

    @Test
    void shouldNotGetExclusiveInternshipOfferStudentViewsInvalidPageSize() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getStudentExclusiveOffers("invalid@gmail.com", 0, 0);

        //ASSERT
        StepVerifier.create(internshipOfferFlux).expectError(InvalidPageRequestException.class).verify();
    }

    @Test
    void shouldGetGeneralInternshipOfferStudentViews() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<InternshipOffer> internshipOffers = InternshipOfferMockData.getListInternshipOffer(2);
        List<InternshipOfferStudentViewDto> internshipOfferStudentViewDtos = InternshipOfferMockData
                .getListInternshipOfferStudentViewDto(2);

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(internshipOfferRepository.findAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(Flux.fromIterable(internshipOffers));

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getGeneralInternshipOffers(0, 2, student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferFlux).assertNext(
                offer -> assertEquals(offer.getId(), internshipOfferStudentViewDtos.get(0).getId()))
                .assertNext(offer -> assertEquals(offer.getId(),
                        internshipOfferStudentViewDtos.get(1).getId()))
                .verifyComplete();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferStudentViewsInvalidPage() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getGeneralInternshipOffers(0, 0, student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferFlux).expectError(InvalidPageRequestException.class).verify();
    }

    @Test
    void shouldGetGeneralInternshipOfferPageCount() {
        //ARRANGE
        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(internshipOfferRepository.countAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(any(), any()))
                .thenReturn(Mono.just(1L));
        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(1);

        //ASSERT
        StepVerifier.create(pageCountMono).expectNext(1L).verifyComplete();
    }


    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidPageSize() {
        //ARRANGE && ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(0);

        //ASSERT
        StepVerifier.create(pageCountMono).expectError(InvalidPageRequestException.class).verify();
    }

    @Test
    void shouldGetExclusiveInternshipOfferPageCount() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(student.getEmail(), 1);

        //ASSERT
        StepVerifier.create(pageCountMono).expectNext((long) student.getExclusiveOffersId().size())
                .verifyComplete();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidSize() {
        //ARRANGE && ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount("email@gmail.com", 0);

        //ASSERT
        StepVerifier.create(pageCountMono).expectError(InvalidPageRequestException.class).verify();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountNullEmail() {
        //ARRANGE && ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(null, 1);

        //ASSERT
        StepVerifier.create(pageCountMono).expectError(UserNotFoundException.class).verify();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidEmail() {
        //ARRANGE
        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount("invalid@gmail.com", 1);

        //ASSERT
        StepVerifier.create(pageCountMono).expectError(UserNotFoundException.class).verify();
    }

    @Test
    void shouldChangeInternshipOfferExclusivityWhenIsTrue() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferRepository.findById(internshipOffer.getId())).thenReturn(Mono.just(internshipOffer));
        when(internshipOfferRepository.save(any())).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.changeInternshipOfferExclusivity(internshipOffer.getId(), true);

        //ASSERT
        StepVerifier.create(internshipOfferMono)
                .assertNext(i -> assertTrue(i.getIsExclusive()))
                .verifyComplete();
    }

    @Test
    void shouldChangeInternshipOfferExclusivityAndRemoveIdFromExclusiveOfferOfStudentWhenIsFalse() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        internshipOffer.setId(student.getExclusiveOffersId().stream().findFirst().get());

        when(internshipOfferRepository.findById(internshipOffer.getId())).thenReturn(Mono.just(internshipOffer));
        when(studentService.getAllStudentContainingExclusiveOffer(any())).thenReturn(Flux.fromIterable(Arrays.asList(student)));
        when(studentService.save(any())).thenReturn(Mono.just(student));
        when(internshipOfferRepository.save(any())).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.changeInternshipOfferExclusivity(internshipOffer.getId(), false);

        //ASSERT
        StepVerifier.create(internshipOfferMono)
                .assertNext(i -> {
                    assertFalse(i.getIsExclusive());
                    assertFalse(student.getExclusiveOffersId().contains(i.getId()));
                })
                .verifyComplete();
    }

    @Test
    void shouldNotChangeInternshipOfferExclusivityWhenNotFound() {
        //ARRANGE
        String id = "234dsd2egd54ter";
        when(internshipOfferRepository.findById(id)).thenReturn(Mono.empty());

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.changeInternshipOfferExclusivity(id, false);

        //ASSERT
        StepVerifier.create(internshipOfferMono)
                .verifyError(InternshipOfferNotFoundException.class);
    }

    @Test
    void shouldNotChangeInternshipOfferExclusivityWhenItIsNotValidated() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        internshipOffer.setIsValidated(false);

        when(internshipOfferRepository.findById(internshipOffer.getId())).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.changeInternshipOfferExclusivity(internshipOffer.getId(), false);

        //ASSERT
        StepVerifier.create(internshipOfferMono)
                .verifyError(ForbiddenActionException.class);
    }

    @Test
    void shouldValidateInternshipOffer() {
        //ARRANGE
        String id = "id";
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        InternshipOfferService internshipOfferServiceSpy = spy(internshipOfferService);

        when(internshipOfferRepository.findById(id)).thenReturn(Mono.just(internshipOffer));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));
        doReturn(Mono.just(new Notification())).when(internshipOfferServiceSpy).createInternshipOfferValidationNotification(any(), any(Boolean.class));
        doReturn(Mono.just(new Notification())).when(internshipOfferServiceSpy).createNewInternshipNotification();

        //ACT
        Mono<InternshipOffer> internshipOfferDtoMono = internshipOfferServiceSpy.validateInternshipOffer(id, true, "");

        //ASSERT
        StepVerifier.create(internshipOfferDtoMono).assertNext(e -> assertTrue(e.getIsValidated()))
                .verifyComplete();
    }

    @Test
    void shouldNotValidateInternshipOffer() {
        //ARRANGE
        String id = "234dsd2egd54ter";

        when(internshipOfferRepository.findById(id)).thenReturn(Mono.empty());

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.validateInternshipOffer(id, true, "");

        //ASSERT
        StepVerifier.create(internshipOfferMono).expectError(InternshipOfferNotFoundException.class).verify();
    }

    @Test
    void shouldAddExclusiveOfferToStudents() {
        //ARRANGE
        List<Student> students = StudentMockData.getListStudent(3)
                .stream().peek(student -> student.setExclusiveOffersId(new HashSet<>())).collect(Collectors.toList());
        Set<String> studentEmails = students.stream().map(Student::getEmail).collect(Collectors.toSet());
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(studentService.findAllByEmails(studentEmails)).thenReturn(Flux.fromIterable(students));
        when(internshipOfferRepository.existsByIdAndIsExclusiveTrue(internshipOffer.getId())).thenReturn(Mono.just(true));
        students.forEach(s -> when(studentService.save(s)).thenReturn(Mono.just(s)));

        //ACT
        Mono<Long> count = internshipOfferService.addExclusiveOfferToStudents(internshipOffer.getId(), studentEmails);

        //ASSERT
        StepVerifier.create(count)
                .assertNext(c -> assertEquals(3, c))
                .verifyComplete();
    }

    @Test
    void shouldAddExclusiveOfferToStudentsWhenInternshipOfferDoNotExistOrNotExclusive() {
        //ARRANGE
        List<Student> students = StudentMockData.getListStudent(3)
                .stream().peek(student -> student.setExclusiveOffersId(new HashSet<>())).collect(Collectors.toList());
        Set<String> studentEmails = students.stream().map(Student::getEmail).collect(Collectors.toSet());
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(internshipOfferRepository.existsByIdAndIsExclusiveTrue(internshipOffer.getId())).thenReturn(Mono.just(false));

        //ACT
        Mono<Long> count = internshipOfferService.addExclusiveOfferToStudents(internshipOffer.getId(), studentEmails);

        //ASSERT
        StepVerifier.create(count)
                .verifyError(ForbiddenActionException.class);
    }

    @Test
    void shouldGetNotYetValidatedInternshipOffer() {
        //ARRANGE
        String semesterFullName = SemesterName.FALL + "-" + LocalDateTime.now().getYear();
        Semester semester = SemesterMockData.getListSemester().get(0);

        when(semesterService.findByFullName(semesterFullName)).thenReturn(Mono.just(semester));
        when(internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalseAndLimitDateToApplyIsBetween(semester.getFrom(), semester.getTo()))
                .thenReturn(InternshipOfferMockData.getNonValidatedInternshipOffers());

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferService.getNotYetValidatedInternshipOffers(semesterFullName);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(i -> assertTrue(!i.getIsValidated() && i.getValidationDate() == null))
                .assertNext(i -> assertTrue(!i.getIsValidated() && i.getValidationDate() == null))
                .verifyComplete();
    }

    @Test
    void shouldGetInternshipOfferStudentInterest() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        String monitorEmail = MonitorMockData.getMockMonitor().getEmail();
        String semesterFullName = SemesterName.FALL + "-" + LocalDateTime.now().getYear();
        List<InternshipOfferStudentInterestViewDto> internshipOffers = InternshipOfferMockData.getListInternshipOfferStudentInterestViewDto(2);

        when(semesterService.findByFullName(semesterFullName)).thenReturn(Mono.just(semester));

        when(internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                any(String.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        ))
                .thenReturn(Flux.fromIterable(InternshipOfferMockData.getListInternshipOffer(3)));

        internshipOffers.forEach(internshipOffer ->
                when(studentService.findAllByEmails(any())).thenReturn(Flux.fromIterable(internshipOffer.getInterestedStudentList())));

        //ACT
        Flux<InternshipOfferStudentInterestViewDto> internshipOfferDtoFlux = internshipOfferService.getInterestedStudents(monitorEmail, semesterFullName);

        //ASSERT
        StepVerifier.create(internshipOfferDtoFlux)
                .assertNext(internshipDto -> assertEquals(2, internshipDto.getInterestedStudentList().size()))
                .assertNext(internshipDto -> assertEquals(2, internshipDto.getInterestedStudentList().size()))
                .assertNext(internshipDto -> assertEquals(2, internshipDto.getInterestedStudentList().size()))
                .verifyComplete();
    }

    @Test
    void shouldApplyInternshipOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();
        Student student = StudentMockData.getMockStudent();

        when(internshipOfferRepository.findById(any(String.class))).thenReturn(Mono.just(internshipOffer));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));
        when(studentService.addOfferToStudentAppliedOffers(any(Student.class), any(String.class))).then(s -> {
            student.getAppliedOffersId().add(internshipOffer.getId());
            return Mono.just(student);
        });

        int sizeBefore = InternshipOfferMockData.getInternshipOffer().getListEmailInterestedStudents().size();

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.applyOffer(internshipOffer.getId(),
                student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferMono).assertNext(
                offer -> assertEquals(sizeBefore + 1, offer.getListEmailInterestedStudents().size()))
                .verifyComplete();
    }

    @Test
    void shouldNotApplyInternshipOfferInvalidStudent() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();
        Student student = StudentMockData.getMockStudent();
        internshipOffer.getListEmailInterestedStudents().add(student.getEmail());

        when(internshipOfferRepository.findById(internshipOffer.getId()))
                .thenReturn(Mono.just(internshipOffer));
        when(studentService.findByEmail(student.getEmail())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.applyOffer(internshipOffer.getId(),
                student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferMono).expectError(UserNotFoundException.class).verify();
    }

    @Test
    void shouldNotApplyInternshipOfferInvalidOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();
        Student student = StudentMockData.getMockStudent();
        internshipOffer.getListEmailInterestedStudents().add(student.getEmail());

        when(internshipOfferRepository.findById(internshipOffer.getId()))
                .thenReturn(Mono.error(InternshipOfferNotFoundException::new));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.applyOffer(internshipOffer.getId(),
                student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferMono).expectError(InternshipOfferNotFoundException.class).verify();
    }

    @Test
    void shouldNotApplyToInternshipOfferNotIncludedInStudentExclusiveOffers() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();
        internshipOffer.setIsExclusive(true);
        Student student = StudentMockData.getMockStudent();

        when(internshipOfferRepository.findById(internshipOffer.getId()))
                .thenReturn(Mono.just(internshipOffer));

        when(studentService.findByEmail(student.getEmail())).thenReturn(Mono.just(student));

        //ACT
        Mono<InternshipOffer> internshipOfferMono = internshipOfferService.applyOffer(internshipOffer.getId(),
                student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferMono).expectError(UnauthorizedException.class).verify();
    }

    @Test
    void shouldMonitorOffersInterestedStudentsContainsStudentEmail() {
        //ARRANGE
        String monitorEmail = "sender";
        String studentEmail = "student1@email.com";
        Flux<InternshipOffer> internshipOffers = InternshipOfferMockData.getAllInternshipOffers();

        when(internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrue(any()))
                .thenReturn(internshipOffers);


        //ACT
        Mono<Boolean> response = internshipOfferService
                .isStudentEmailInMonitorOffersInterestedStudents(studentEmail, monitorEmail);

        //ASSERT
        StepVerifier.create(response).assertNext(Assertions::assertTrue).verifyComplete();
    }

    @Test
    void shouldNotMonitorOffersInterestedStudentsContainsStudentEmail() {
        //ARRANGE
        Flux<InternshipOffer> internshipOffers = InternshipOfferMockData.getAllInternshipOffers();

        when(internshipOfferRepository.findAllByMonitorEmailAndIsValidatedTrue(any()))
                .thenReturn(internshipOffers);

        String monitorEmail = "sender";
        String studentEmail = "wrong@email.com";

        //ACT
        Mono<Boolean> response = internshipOfferService
                .isStudentEmailInMonitorOffersInterestedStudents(studentEmail, monitorEmail);

        //ASSERT
        StepVerifier.create(response).assertNext(Assertions::assertFalse).verifyComplete();
    }

    @Test
    void shouldGetGeneralInternshipOfferStudentViewsAlreadyAppliedMatches() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<InternshipOffer> internshipOffers = InternshipOfferMockData.getListInternshipOffer(2);
        List<InternshipOfferStudentViewDto> internshipOfferStudentViewDtos = InternshipOfferMockData
                .getListInternshipOfferStudentViewDto(2);

        student.getAppliedOffersId().add(internshipOfferStudentViewDtos.get(0).getId());

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(internshipOfferRepository.findAllByIsExclusiveFalseAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(Pageable.class)))
                .thenReturn(Flux.fromIterable(internshipOffers));

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getGeneralInternshipOffers(0, 2, student.getEmail());

        //ASSERT
        StepVerifier.create(internshipOfferFlux).assertNext(offer -> {
            assertEquals(offer.getId(), internshipOfferStudentViewDtos.get(0).getId());
            assertTrue(offer.getHasAlreadyApplied());
        }).assertNext(offer -> {
            assertEquals(offer.getId(), internshipOfferStudentViewDtos.get(1).getId());
            assertFalse(offer.getHasAlreadyApplied());
        }).verifyComplete();
    }

    @Test
    void shouldGetExclusiveInternshipOfferStudentViewsAlreadyAppliedMatches() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<String> exclusiveOfferIds = new ArrayList<>(student.getExclusiveOffersId());

        InternshipOffer internshipOffer1 = InternshipOfferMockData.getInternshipOffer();
        internshipOffer1.setId(exclusiveOfferIds.get(0));

        InternshipOffer internshipOffer2 = InternshipOfferMockData.getInternshipOffer();
        internshipOffer2.setId(exclusiveOfferIds.get(1));

        student.getAppliedOffersId().add(internshipOffer1.getId());

        when(studentService.findByEmail(any(String.class))).thenReturn(Mono.just(student));
        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                same(exclusiveOfferIds.get(0)), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Mono.just(internshipOffer1));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndIsValidatedTrueAndLimitDateToApplyIsBetween(
                same(exclusiveOfferIds.get(1)), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Mono.just(internshipOffer2));

        //ACT
        Flux<InternshipOfferStudentViewDto> internshipOfferFlux = internshipOfferService
                .getStudentExclusiveOffers(student.getEmail(), 0, 2);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(offer -> {
                    assertEquals(offer.getId(), exclusiveOfferIds.get(0));
                    assertTrue(offer.getHasAlreadyApplied());
                })
                .assertNext(offer -> {
                    assertEquals(offer.getId(), exclusiveOfferIds.get(1));
                    assertFalse(offer.getHasAlreadyApplied());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetAllNonValidatedOffers() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Flux<InternshipOffer> internshipOffers = InternshipOfferMockData.getAllInternshipOffers();

        when(semesterService.findByFullName(any())).thenReturn(Mono.just(semester));
        when(internshipOfferRepository.findAllByIsValidatedFalseAndLimitDateToApplyIsBetween(any(), any())).thenReturn(internshipOffers);

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferService.getAllNonValidatedOffers(semester.getFullName());

        //ASSERT
        StepVerifier.create(internshipOfferFlux).assertNext(s -> {
            assertEquals(InternshipOfferMockData.getFirstInternshipOffer(), s);
        }).assertNext(s -> {
            assertEquals(InternshipOfferMockData.getSecondInternshipOffer(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetAllValidatedOffers() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Flux<InternshipOffer> internshipOffers = InternshipOfferMockData.getAllInternshipOffers();

        when(semesterService.findByFullName(any())).thenReturn(Mono.just(semester));
        when(internshipOfferRepository.findAllByIsValidatedTrueAndLimitDateToApplyIsBetween(any(), any())).thenReturn(internshipOffers);

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferService.getAllValidatedOffers(semester.getFullName());

        //ASSERT
        StepVerifier.create(internshipOfferFlux).assertNext(s -> {
            assertEquals(InternshipOfferMockData.getFirstInternshipOffer(), s);
        }).assertNext(s -> {
            assertEquals(InternshipOfferMockData.getSecondInternshipOffer(), s);
        }).verifyComplete();
    }

}
