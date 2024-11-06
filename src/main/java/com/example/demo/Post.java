
package com.example.demo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "cda1")
public class Post {
    @Id
    private String id;

    private List<Author> authors;
    private String title;
    private String body;
}
