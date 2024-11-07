package com.example.demo;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post>findByTitle(String post);
}
