package com.example.finalproj.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        // Use the provided secret to generate a secure key
        byte[] keyBytes = jwtSecret.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(Map<String, Object> userInfo) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject((String) userInfo.get("email"))
                .claim("name", userInfo.get("name"))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}