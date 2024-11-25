package com.example.demo.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public abstract class TokenCreator {
    abstract SecretKey getSecretKey();

    public long expirationDay() {
        return 1;
    }

    public String createToken(String username) {
        long daysInMs = (long) 1000 * 60 * 60 * 24 * expirationDay();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + daysInMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaims(token)
                .getSubject();
    }
}
