package com.example.demo.token;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


@Component
public class AccessToken extends TokenCreator {

    @Override
    public SecretKey getSecretKey() {
        String  secretKey="gbidonswr43423234234edfgdfgdfgdfgdfgdfgdfgdfgfe";
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    @Override
    public long expirationDay() {
        return 7;
    }
}
