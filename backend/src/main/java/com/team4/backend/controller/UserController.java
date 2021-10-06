package com.team4.backend.controller;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.exception.WrongCredentialsException;
import com.team4.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthRequestDto authRequestDto) {
        return userService.login(authRequestDto)
                .flatMap(token -> Mono.just(ResponseEntity.ok().body(token)))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage())));
    }

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<Boolean>> userExistsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email)
                .flatMap(b -> Mono.just(ResponseEntity.ok().body(b)));
    }

}
