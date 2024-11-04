
package com.example.demo;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import java.util.List;
@Data
@Builder
@Document(collection="cda1")
public class Post {
    @Id
    private ObjectId id;

    private List<Author> authors;
    private String title;
    private String body;


}
