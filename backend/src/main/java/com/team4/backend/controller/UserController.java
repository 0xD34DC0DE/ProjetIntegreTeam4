package com.team4.backend.controller;

import com.team4.backend.model.User;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.model.dto.AuthResponse;
import com.team4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return userService.login(authRequest);
    }
}
