package com.team4.backend.service;

import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import com.team4.backend.testdata.SemesterMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {

    @Mock
    SemesterRepository semesterRepository;

    @InjectMocks
    SemesterService semesterService;

    @Test
    void shouldCreateSemester() {
        //ARRANGE
        Semester semester = SemesterMockData.getSemester();

        when(semesterRepository.save(any())).thenReturn(Mono.just(semester).map(s -> {
            s.setId("615259e03835be1f53bd49e4");
            return s;
        }));

        //ACT
        Mono<Semester> semesterMono = semesterService.create(semester.getFullName(), semester.getFrom(), semester.getTo());

        //ASSERT
        StepVerifier.create(semesterMono)
                .assertNext(s -> assertEquals(semester.getFullName(), s.getFullName()))
                .verifyComplete();
    }

}
