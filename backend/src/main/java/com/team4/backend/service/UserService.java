package com.team4.backend.service;

import com.team4.backend.model.dto.AuthRequest;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.JwtUtil;
import com.team4.backend.util.PBKDF2Encoder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Mono<String> login(AuthRequest authRequest){
        return userRepository.findByEmailAndPassword(authRequest.getEmail(), pbkdf2Encoder.encode(authRequest.getPassword()))
                .map(user -> jwtUtil.generateToken(user));

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
