package com.example.demo.token;

import org.springframework.stereotype.Service;

@Service
public class TokenDriver {
    private final AccessToken accessToken;
    private final RefreshToken refreshToken;

    public TokenDriver(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenCreator getTokenCreatorByType(TokenType type) throws TokenTypeNotFoundException {
        switch (type) {
            case JWT_ACCESS_TOKEN -> {
                return accessToken;
            }

            case JWT_REFRESH_TOKEN -> {
                return refreshToken;
            }

            default -> {
                throw new TokenTypeNotFoundException("Invalid token type: " + type);
            }
        }
    }

    public static class TokenTypeNotFoundException extends Exception {
        public TokenTypeNotFoundException() {
        }

        public TokenTypeNotFoundException(String message) {
            super(message);
        }

        public TokenTypeNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public TokenTypeNotFoundException(Throwable cause) {
            super(cause);
        }

        public TokenTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
