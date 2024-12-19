package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationAuthenticationRequest {
    private String username;
    private String password;
    private String email;
}
