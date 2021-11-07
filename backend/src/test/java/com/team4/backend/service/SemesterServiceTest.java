package com.team4.backend.service;

import com.team4.backend.exception.SemesterNotFoundException;
import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import com.team4.backend.testdata.SemesterMockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {

    @Mock
    SemesterRepository semesterRepository;

    @InjectMocks
    SemesterService semesterService;

    @Test
    void shouldInitializeSemestersAnnually() {
        //ARRANGE
        List<Semester> semesters = SemesterMockData.getListSemester();

        when(semesterRepository.saveAll(semesters)).thenReturn(Flux.fromIterable(semesters));

        //ACT
        Flux<Semester> semesterFlux = semesterService.initializeSemestersAnnually(semesters);

        //ASSERT
        StepVerifier.create(semesterFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldFindSemesterByFullName() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);

        when(semesterRepository.findByFullName(any())).thenReturn(Mono.just(semester));

        //ACT
        Mono<Semester> semesterMono = semesterService.findByFullName(semester.getFullName());

        //ASSERT
        StepVerifier.create(semesterMono)
                .assertNext(s -> Assertions.assertEquals(semester.getFullName(), s.getFullName()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindSemesterByFullName() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);

        when(semesterRepository.findByFullName(any())).thenReturn(Mono.error(SemesterNotFoundException::new));

        //ACT
        Mono<Semester> semesterMono = semesterService.findByFullName(semester.getFullName());

        //ASSERT
        StepVerifier.create(semesterMono)
                .verifyError(SemesterNotFoundException.class);
    }

    @Test
    void shouldGetCurrentSemester() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);

        when(semesterRepository.findByFromLessThanEqualAndToGreaterThanEqual(any(), any())).thenReturn(Mono.just(semester));

        //ACT
        Mono<String> semesterMono = semesterService.getCurrentSemesterFullName();

        //ASSERT
        StepVerifier.create(semesterMono)
                .assertNext(s -> Assertions.assertEquals(semester.getFullName(), s))
                .verifyComplete();
    }

    @Test
    void shouldNotGetCurrentSemester() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);

        when(semesterRepository.findByFromLessThanEqualAndToGreaterThanEqual(any(), any())).thenReturn(Mono.error(SemesterNotFoundException::new));

        //ACT
        Mono<String> semesterMono = semesterService.getCurrentSemesterFullName();

        //ASSERT
        StepVerifier.create(semesterMono)
                .verifyError(SemesterNotFoundException.class);
    }

}
