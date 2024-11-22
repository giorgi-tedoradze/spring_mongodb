package com.example.demo.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public interface TokenCreator {
    SecretKey getSecretKey();
    default long expirationDay(){return 1;}

    default String getToken(String name) {
        long daysInMs = (long) 1000 * 60 * 60 * 24 * expirationDay();

        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + daysInMs))
                .signWith(getSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    default Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    default   boolean  isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    default String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }
}
