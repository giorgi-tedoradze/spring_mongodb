
package com.example.demo;
import lombok.Data;
import lombok.Builder;
@Data
@Builder
public class Author {
    private String name;
    private String username;
    private String password;
    private String email;
    private String website;
}

