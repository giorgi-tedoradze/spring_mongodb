package com.example.demo.token;

import com.example.demo.model.TokenAuthenticationData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TokenCreator {
    abstract SecretKey getSecretKey();

    public long expirationDay() {
        return 1;
    }

    public String createToken(TokenAuthenticationData tokenAuthenticationData) {
        long daysInMs = (long) 1000 * 60 * 60 * 24 * expirationDay();

        return Jwts.builder()
                .setSubject(tokenAuthenticationData.getUsername())
                .claim("roles", tokenAuthenticationData.getRole().name())
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

    public List<String> extractRoles(String token) {
        List<?> roles = extractClaims(token).get("roles", List.class);
        return roles.stream()
                .filter(role -> role instanceof String)
                .map(role -> (String) role)
                .collect(Collectors.toList());
    }

    public Collection<? extends GrantedAuthority> extractRole(String toke) {
        return List.of(new SimpleGrantedAuthority(extractRoles(toke).get(0)));
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