package com.team4.backend.util;


import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = {JwtUtil.class})
@EnableAutoConfiguration
public class JwtUtilTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test
    void generateToken() {
        //ARRANGE
        User user = User.builder().registrationNumber("123456789").role(Role.ROLE_STUDENT).build();

        //ACT
        String token = jwtUtil.generateToken(user);

        //ASSERT
        assertEquals(user.getRegistrationNumber(), Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(token).getBody().getSubject());
    }

    @Test
    void getAllClaimsFromToken(){
        //ARRANGE
        User user = User.builder().registrationNumber("123456789").role(Role.ROLE_STUDENT).build();
        Map<String, Role> claims = new HashMap<>();

        claims.put("role",user.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getRegistrationNumber())
                .setIssuedAt(new Date())
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        Claims returnedClaims = jwtUtil.getAllClaimsFromToken(token);

        //ASSERT
        assertEquals(user.getRegistrationNumber(),returnedClaims.getSubject());
    }

    @Test
    void isTokenExpired(){
        //ARRANGE
        User user = User.builder().registrationNumber("123456789").role(Role.ROLE_STUDENT).build();
        Map<String, Role> claims = new HashMap<>();
        Date creationDate = new Date();

        claims.put("role",user.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getRegistrationNumber())
                .setIssuedAt(creationDate)
                .setExpiration(new Date(creationDate.getTime() + 100000))
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        boolean isTokenExpired = jwtUtil.isTokenExpired(token);

        //ASSERT
        assertFalse(isTokenExpired);
    }

    @Test
    void getRegistrationNumberFromToken(){
        //ARRANGE
        User user = User.builder().registrationNumber("123456789").role(Role.ROLE_STUDENT).build();
        Map<String, Role> claims = new HashMap<>();
        Date creationDate = new Date();

        claims.put("role",user.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getRegistrationNumber())
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        String returnedRegistrationNumber = jwtUtil.getRegistrationNumberFromToken(token);

        //ASSERT
        assertEquals(user.getRegistrationNumber(),returnedRegistrationNumber);
    }
}
