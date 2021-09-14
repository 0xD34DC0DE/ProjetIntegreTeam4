package com.team4.backend.util;


import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
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
        User user = User.builder().email("123456789@claurendeau.qc.ca").role(Role.STUDENT).build();

        //ACT
        String token = jwtUtil.generateToken(user);

        //ASSERT
        assertEquals(user.getEmail(), Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(token).getBody().getSubject());
    }

    @Test
    void getAllClaimsFromToken() {
        //ARRANGE
        User user = User.builder().email("123456789@claurendeau.qc.ca").role(Role.STUDENT).build();
        Map<String, Role> claims = new HashMap<>();

        claims.put("role",user.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        Claims returnedClaims = jwtUtil.getAllClaimsFromToken(token);

        //ASSERT
        assertEquals(user.getEmail(),returnedClaims.getSubject());
    }

    @Test
    void isTokenExpired() {
        //ARRANGE
        User user = User.builder().email("123456789@claurendeau.qc.ca").role(Role.STUDENT).build();
        Map<String, Role> claims = new HashMap<>();
        Date creationDate = new Date();

        claims.put("role",user.getRole());

        String token1 = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(creationDate)
                .setExpiration(new Date(creationDate.getTime() + 100000))
                .signWith(jwtUtil.getKey())
                .compact();

        String token2 = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(creationDate)
                .setExpiration(new Date(creationDate.getTime() + -1000))
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        boolean isTokenExpired1 = jwtUtil.isTokenExpired(token1);
        boolean isTokenExpired2 = jwtUtil.isTokenExpired(token2);

        //ASSERT
        assertFalse(isTokenExpired1);
        assertTrue(isTokenExpired2);
    }

    @Test
    void getEmailFromToken(){
        //ARRANGE
        User user = User.builder().email("123456789@claurendeau.qc.ca").role(Role.STUDENT).build();
        Map<String, Role> claims = new HashMap<>();

        claims.put("role",user.getRole());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .signWith(jwtUtil.getKey())
                .compact();

        //ACT
        String returnedEmail = jwtUtil.getEmailFromToken(token);

        //ASSERT
        assertEquals(user.getEmail(),returnedEmail);
    }

    @Test
    void isTokenValid(){
        //ARRANGE
        String token1 = Jwts.builder()
                .signWith(jwtUtil.getKey())
                .compact();

        String token2 = "dasdasdasdsad";
        //ACT
        boolean isTokenValid1 = jwtUtil.isTokenValid(token1);
        boolean isTokenValid2 = jwtUtil.isTokenValid(token2);

        //ASSERT
        assertTrue(isTokenValid1);
        assertFalse(isTokenValid2);

    }
}
