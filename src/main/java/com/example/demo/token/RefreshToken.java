package com.example.demo.token;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class RefreshToken extends TokenCreator {
    @Override
    public SecretKey getSecretKey() {
        String secretKey = "gbidonswr43423234234edfgdfgdfgdfgdfgdfgdfgdfgfe";
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public long expirationDay() {
        return 30;
    }
}
