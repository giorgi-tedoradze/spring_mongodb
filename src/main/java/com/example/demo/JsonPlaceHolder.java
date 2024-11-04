
package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="jsonplaceholder",url ="https://jsonplaceholder.typicode.com/")

public interface JsonPlaceHolder {
    @GetMapping("/posts")
    List<Post> getAllPosts();
    @GetMapping("/users")
    List<Author> getAllAuthors();
}
