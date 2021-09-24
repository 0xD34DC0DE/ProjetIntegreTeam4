package com.team4.backend.controller;

import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.Student;
import com.team4.backend.service.StudentService;
import com.team4.backend.service.UserService;
import com.team4.backend.testdata.StudentMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = StudentController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    StudentService studentService;

    @MockBean
    UserService userService;

    @Test
    public void shouldCreateStudent() {
        //ARRANGE
        StudentDto studentDto = StudentMockData.getMockStudentDto();

        studentDto.setId(null);

        Student student = StudentMockData.getMockStudent();

        when(studentService.registerStudent(any(Student.class)))
                .thenReturn(
                        Mono.just(student)
                                .map(s -> {
                                    s.setId("some_id");
                                    return s;
                                })
                );

        when(userService.findByEmail(any(String.class))).thenReturn(Mono.empty());

        //ACT
        webTestClient
                .post().uri("/student/register").bodyValue(studentDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    public void shouldNotCreateStudent() {
        //ARRANGE
        StudentDto studentDto = StudentMockData.getMockStudentDto();

        studentDto.setId(null);

        Student alreadyExistingStudent = StudentMockData.getMockStudent();
        Student student = StudentMockData.getMockStudent();

        when(userService.findByEmail(any(String.class))).thenReturn(Mono.just(alreadyExistingStudent));

        //ACT
        webTestClient
                .post().uri("/student/register").bodyValue(studentDto)
                .exchange()
                // ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody().isEmpty();
    }
}
