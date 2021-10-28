package com.team4.backend.service;

import com.team4.backend.model.Internship;
import com.team4.backend.repository.InternshipRepository;
import com.team4.backend.testdata.InternshipMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

    @Mock
    InternshipRepository internshipRepository;

    @InjectMocks
    InternshipService internshipService;


    //TODO:Service test does not work and Controller test is not done
    @Test
    void shouldGetInternshipByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();

        when(internshipRepository.findByStudentEmail(internship.getStudentEmail())).thenReturn(Mono.just(internship));

        //ACT
        Mono<Internship> internshipMono = internshipService.getInternshipByEmail(internship.getStudentEmail());

        //ASSERT
        StepVerifier
                .create(internshipMono)
                .assertNext(i -> assertEquals(internship.getStudentEmail(), i.getStudentEmail()))
                .verifyComplete();
    }
}
