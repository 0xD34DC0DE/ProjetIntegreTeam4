package com.team4.backend.service;

import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import com.team4.backend.testdata.SemesterMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

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

}
