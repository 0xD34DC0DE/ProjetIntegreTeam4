package com.team4.backend.util;


import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
