package com.team4.backend.util;

import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@Log
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

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        Long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        claims.put("role", user.getRole().toString());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("phoneNumber", user.getPhoneNumber());

        if(user.getRole().equals(Role.MONITOR))
            claims.put("companyName",((Monitor) user).getCompanyName());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        try {

            return getAllClaimsFromToken(token).getExpiration().before(new Date());
        }catch (Exception e){
            log.info(e.getMessage());
            return true;
        }
    }

    public String getEmailFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().isSigned(token);
    }


}
