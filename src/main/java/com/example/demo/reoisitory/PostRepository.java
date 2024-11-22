package com.example.demo.reoisitory;


import com.example.demo.Post;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PostRepository extends MongoRepository<Post, String> {

}
