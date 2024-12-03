package com.example.demo.model;


import com.example.demo.database.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenAuthenticationData {
    String username;
    Role role;

    public TokenAuthenticationData(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public TokenAuthenticationData(String username, Role role) {
        this.username = username;
        this.role = role;
    }
}
