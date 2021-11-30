package com.team4.backend.controller;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.dto.UserDto;
import com.team4.backend.mapping.UserMapper;
import com.team4.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
                .flatMap(token -> Mono.just(ResponseEntity.ok().body(token)));
    }

    @GetMapping("/email/{email}")
    public Mono<Boolean> userExistsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('INTERNSHIP_MANAGER','SUPERVISOR')")
    public Flux<UserDto> getAll(@RequestParam String role) {
        return userService.getAll(role.toUpperCase()).map(UserMapper::toDto);
    }

}
