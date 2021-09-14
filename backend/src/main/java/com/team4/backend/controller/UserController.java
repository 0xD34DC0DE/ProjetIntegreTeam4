package com.team4.backend.controller;

import com.team4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Mono<String> login(@RequestParam("email") String email, @RequestParam("password") String password){
        return userService.login(email,password);
    }
}
