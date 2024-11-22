package com.example.demo.token;

import org.springframework.stereotype.Component;

@Component
public class AccessToken implements TokenCreator {
    @Override
    public String getSecretKey() {
        return "gbidonswr43423234234edfgdfgdfgdfgdfgdfgdfgdfgfe";
    }

    @Override
    public long expirationDay() {
        return 7;
    }
}
