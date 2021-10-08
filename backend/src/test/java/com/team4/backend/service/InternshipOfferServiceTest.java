package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.exception.InvalidPageRequestException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.testdata.InternshipOfferMockData;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@Log
@ExtendWith(MockitoExtension.class)
public class InternshipOfferServiceTest {

    @Mock
    InternshipOfferRepository internshipOfferRepository;

    @Mock
    MonitorService monitorService;

    @Mock
    StudentService studentService;

    @InjectMocks
    InternshipOfferService internshipOfferService;

    @Test
    void shouldCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(true));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldNotCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(false));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .expectError(UserNotFoundException.class)
                .verify();
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

        when(studentService.getStudent(any(String.class))).thenReturn(Mono.just(student));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                same(exclusiveOfferIds.get(0)), any(LocalDate.class))
        ).thenReturn(Mono.just(internshipOffer1));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                same(exclusiveOfferIds.get(1)), any(LocalDate.class))
        ).thenReturn(Mono.just(internshipOffer2));

        //ACT
        Flux<InternshipOffer> internshipOfferFlux =
                internshipOfferService.getStudentExclusiveOffers(student.getEmail(), 0, 2);

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

        when(studentService.getStudent(any(String.class))).thenReturn(Mono.just(student));

        when(internshipOfferRepository.findByIdAndIsExclusiveTrueAndLimitDateToApplyAfter(
                same(exclusiveOfferIds.get(1)), any(LocalDate.class))
        ).thenReturn(Mono.just(internshipOffer));

        //ACT
        Flux<InternshipOffer> internshipOfferFlux =
                internshipOfferService.getStudentExclusiveOffers(student.getEmail(), 1, 1);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(offer -> assertEquals(offer.getId(), exclusiveOfferIds.get(1)))
                .verifyComplete();
    }

    @Test
    void shouldNotGetExclusiveInternshipOfferStudentViewsInvalidEmail() {
        //ARRANGE
        when(studentService.getStudent(any(String.class))).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Flux<InternshipOffer> internshipOfferFlux =
                internshipOfferService.getStudentExclusiveOffers("invalid@gmail.com", 0, 1);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void shouldNotGetExclusiveInternshipOfferStudentViewsInvalidPageSize() {
        //ARRANGE

        //ACT
        Flux<InternshipOffer> internshipOfferFlux =
                internshipOfferService.getStudentExclusiveOffers("invalid@gmail.com", 0, 0);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }

    @Test
    void shouldGetGeneralInternshipOfferStudentViews() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        List<InternshipOffer> internshipOffers = InternshipOfferMockData.getListInternshipOffer(2);
        List<InternshipOfferStudentViewDto> internshipOfferStudentViewDtos =
                InternshipOfferMockData.getListInternshipOfferStudentViewDto(2);

        when(internshipOfferRepository.findAllByIsExclusiveFalseAndLimitDateToApplyAfter(
                any(LocalDate.class), any(Pageable.class))
        ).thenReturn(Flux.fromIterable(internshipOffers));

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferService.getGeneralInternshipOffers(0, 2);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .assertNext(offer -> assertEquals(offer.getId(), internshipOfferStudentViewDtos.get(0).getId()))
                .assertNext(offer -> assertEquals(offer.getId(), internshipOfferStudentViewDtos.get(1).getId()))
                .verifyComplete();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferStudentViewsInvalidPage() {
        //ARRANGE

        //ACT
        Flux<InternshipOffer> internshipOfferFlux = internshipOfferService.getGeneralInternshipOffers(0, 0);

        //ASSERT
        StepVerifier.create(internshipOfferFlux)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }

    @Test
    void shouldGetGeneralInternshipOfferPageCount() {
        //ARRANGE
        when(internshipOfferRepository.countAllByIsExclusiveFalseAndLimitDateToApplyAfter(any(LocalDate.class)))
                .thenReturn(Mono.just(1L));
        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(1);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidPageSize() {
        //ARRANGE

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(0);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }

    @Test
    void shouldGetExclusiveInternshipOfferPageCount() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        when(studentService.getStudent(any(String.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(student.getEmail(), 1);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectNext((long) student.getExclusiveOffersId().size())
                .verifyComplete();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidSize() {
        //ARRANGE

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount("email@gmail.com", 0);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountNullEmail() {
        //ARRANGE

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount(null, 1);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void shouldNotGetGeneralInternshipOfferPageCountInvalidEmail() {
        //ARRANGE
        when(studentService.getStudent(any(String.class))).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<Long> pageCountMono = internshipOfferService.getInternshipOffersPageCount("invalid@gmail.com", 1);

        //ASSERT
        StepVerifier.create(pageCountMono)
                .expectError(UserNotFoundException.class)
                .verify();
    }
}
