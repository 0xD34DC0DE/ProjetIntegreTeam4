package com.team4.backend.controller;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.exception.WrongCredentialsException;
import com.team4.backend.model.User;
import com.team4.backend.service.UserService;
import com.team4.backend.testdata.UserMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = UserController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @Test
    public void loginSuccess() {
        //ARRANGE
        AuthRequestDto authRequestDto = new AuthRequestDto("testing@gmail.com", "p@77w0rd");

        when(userService.login(authRequestDto)).thenReturn(Mono.just("token_string"));

        //ACT
        webTestClient
                .post()
                .uri("/user/login")
                .bodyValue(authRequestDto)
                .exchange()
                //ASSERT
                .expectStatus()
                .isOk()
                .expectBody(String.class).returnResult().getStatus();

    }

    @Test
    public void loginNoSuccess() {
        //ARRANGE
        AuthRequestDto authRequestDto = new AuthRequestDto("testing@gmail.com", "p@77w0rd");

        when(userService.login(authRequestDto)).thenReturn(Mono.error(WrongCredentialsException::new));

        //ACT
        webTestClient
                .post()
                .uri("/user/login")
                .bodyValue(authRequestDto)
                .exchange()
                //ASSERT
                .expectStatus()
                .isUnauthorized()
                .expectBody(String.class).returnResult().getStatus();

    }

    @Test
    public void isEmailTakenTrue() {
        //ARRANGE
        String email = "testing@gmail.com";

        when(userService.existsByEmail(email)).thenReturn(Mono.just(true));

        //ACT
        webTestClient
                .get()
                .uri("/user/email/testing@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Boolean.class);
    }

    @Test
    public void isEmailTakenFalse() {
        //ARRANGE
        String email = "non_existing@gmail.com";

        when(userService.existsByEmail(email)).thenReturn(Mono.just(false));

        //ACT
        webTestClient
                .get()
                .uri("/user/email/non_existing@gmail.com")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(Boolean.class);
    }

    @Test
    void shouldGetAllByRole(){
        //ARRANGE
        String role = "STUDENT";
        when(userService.getAll(role)).thenReturn(UserMockData.getAllStudents());

        // ACT
        webTestClient
                .get()
                .uri("/user/getAll?role=STUDENT")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(User.class);
    }

}
