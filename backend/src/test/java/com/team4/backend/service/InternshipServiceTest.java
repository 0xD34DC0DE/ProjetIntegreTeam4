package com.team4.backend.service;

import com.team4.backend.model.Internship;
import com.team4.backend.repository.InternshipRepository;
import com.team4.backend.testdata.InternshipMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class InternshipServiceTest {

    @Mock
    private InternshipRepository internshipRepository;

    @InjectMocks
    private InternshipService internshipService;


    //TODO:Service test does not work and Controller test is not done
    @Test
    void shouldGetInternshipByStudentEmail() {
        //ARRANGE
        String studentEmail = "studentTest@gmail.com";
        Internship internship = InternshipMockData.getInternship();

        when(internshipRepository.findInternshipByStudentEmail(studentEmail)).thenReturn(Mono.just(internship));

        //ACT
        Mono<Internship> internshipMono = internshipService.getInternshipByEmail(studentEmail);

        //ASSERT
        StepVerifier
                .create(internshipMono)
                .assertNext(i -> assertEquals(studentEmail, i.getStudentEmail()))
                .verifyComplete();
    }
}
