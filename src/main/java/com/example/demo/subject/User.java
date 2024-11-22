package com.example.demo.subject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="USER")
@Data
public class User{
    @Id
    private String id;

    private String name;
    private String username;
    private String password;
    private String email;

}
