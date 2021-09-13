package com.team4.backend.util;

import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user){
        Map<String, Role> claims = new HashMap<>();
        Long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        claims.put("role",user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getRegistrationNumber())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }



}
