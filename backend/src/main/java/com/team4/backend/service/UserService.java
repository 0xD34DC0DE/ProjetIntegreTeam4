package com.team4.backend.service;

import com.team4.backend.dto.AuthRequestDto;
import com.team4.backend.exception.WrongCredentialsException;
import com.team4.backend.model.User;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.JwtUtil;
import com.team4.backend.util.PBKDF2Encoder;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PBKDF2Encoder pbkdf2Encoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.jwtUtil = jwtUtil;
    }

    public Mono<String> login(AuthRequestDto authRequestDto) {
        return userRepository.findByEmailAndPasswordAndIsEnabledTrue(authRequestDto.getEmail(), pbkdf2Encoder.encode(authRequestDto.getPassword()))
                .map(jwtUtil::generateToken)
                .switchIfEmpty(Mono.error(new WrongCredentialsException("Wrong credentials!")));
    }

    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Flux<User> getAll(String role){
        return userRepository.findAllByRoleEquals(role);
    }

    /*
    TODO --> resetPassword() --> Nice To Have
            -take email of user
            -send url of front-end + randomstring
            -front end will forward request to backend
            -the backend will return true or false, if the verification code is still valid
            -depending on the answer the frontend will forward to the form or not
     */
}
