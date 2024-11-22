

package com.example.demo;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
