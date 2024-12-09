package com.example.demo.model;


import com.example.demo.database.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TokenAuthenticationData {
    String username;

    List<String> role;

    public TokenAuthenticationData(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public TokenAuthenticationData(String username,  List<String> role ) {
        this.username = username;
        this.role = role;
    }
}
