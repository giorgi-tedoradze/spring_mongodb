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
        List<String> roles =tokenAuthenticationData.getRole();
        try {
            return Jwts.builder()
                    .setSubject(tokenAuthenticationData.getUsername())
                    .claim("roles",roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + daysInMs))
                    .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                    .compact();
        }catch (Exception e){
            throw new RuntimeException("TokenCreator::createToken:"+e);
        }



    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            throw new RuntimeException("TokenCreator::extractClaims:"+e);
        }

    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        List<String> roles=extractRolesToString(token);
        List<SimpleGrantedAuthority> Authorities = roles.stream()
                .map(role->new SimpleGrantedAuthority((String)"ROLE_"+ role))
                .toList();

        return Authorities;

    }

    public List<String> extractRolesToString(String token) {
        try {
            Object rolesObject = extractClaims(token).get("roles");
            if (rolesObject instanceof List<?>) {
                return ((List<?>) rolesObject).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }
        }catch (Exception e){
            throw new RuntimeException("TokenCreator::extractRolesToString:"+e);
        }

        throw new IllegalArgumentException("TokenCreator::extractRolesToString: Invalid roles format");

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