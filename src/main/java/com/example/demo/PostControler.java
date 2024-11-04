
package com.example.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/demo/posts")
public class PostControler {
    private final PostRepozitori repozitori;
    private final JsonPlaceHolder cliebt;
    private final List<Author> authors;
    public PostControler(PostRepozitori repozitori, JsonPlaceHolder cliebt) {
        this.repozitori = repozitori;
        this.cliebt = cliebt;
        this.authors = cliebt.getAllAuthors();

    }
    /*public void testauthors(){
        if(authors != null){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n AAAAAAAAAAAAAAAWEq"+authors);
        }
    }*/
    @GetMapping("/")
    private List<Post>findAll() {
        return repozitori.findAll();
    }



    @GetMapping("/test")// damasrule!!!!!
    public List<Post>init() {
        List<Post> posts = cliebt.getAllPosts();
        for (Post post : posts) {
            post.setAuthors(authors);
        }

        repozitori.saveAll(posts);
        if (posts==null || posts.isEmpty()) {
            System.out.println("No posts found");
        }

        return posts;

    }


    @DeleteMapping("deleteAll")
    public void deleteAll() {
        repozitori.deleteAll();
    }

}
