package com.team4.backend.service;

import com.team4.backend.model.Internship;
import com.team4.backend.repository.InternshipRepository;
import com.team4.backend.testdata.InternshipMockData;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void shouldExistsByStudentEmail() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();

        when(internshipRepository.existsByStudentEmail(internship.getStudentEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Boolean> internshipExists = internshipService.existsByStudentEmail(internship.getStudentEmail());

        //ASSERT
        StepVerifier
                .create(internshipExists)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void shouldNotExistsByStudentEmail() {
        //ARRANGE
        String wrongStudentEmail = "wrong_student_email@gmail.com";

        when(internshipRepository.existsByStudentEmail(wrongStudentEmail)).thenReturn(Mono.just(false));

        //ACT
        Mono<Boolean> internshipExists = internshipService.existsByStudentEmail(wrongStudentEmail);

        //ASSERT
        StepVerifier
                .create(internshipExists)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }
}
