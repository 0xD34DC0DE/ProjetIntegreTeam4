package com.team4.backend.service;

import com.team4.backend.TestingRunner;
import com.team4.backend.model.User;
import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.model.dto.AuthResponse;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.JwtUtil;
import com.team4.backend.util.PBKDF2Encoder;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@Log
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PBKDF2Encoder pbkdf2Encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Mono<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return findUserByRegistrationNumberAndPassword(authRequest.getRegistrationNumber(), pbkdf2Encoder.encode(authRequest.getPassword()))
                .map(user -> new AuthResponse(jwtUtil.generateToken(user)));

    }

    /*
    TODO --> resetPassword() --> Nice To Have
            -take email of user
            -send url of front-end + randomstring
            -front end will forward request to backend
            -the backend will return true or false, if the verification code is still valid
            -depending on the answer the frontend will forward to the form or not
     */

    private Mono<User> findUserByRegistrationNumberAndPassword(String registrationNumber,String password){
        return userRepository.findByRegistrationNumberAndPassword(registrationNumber,password);
    }
}
