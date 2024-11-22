//
//package com.example.demo;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//    public PostControler(PostRepository repozitori, JsonPlaceHolder cliebt) {
//        this.repozitori = repozitori;
//        this.cliebt = cliebt;
//        this.authors = cliebt.getAllAuthors();
//
//    }
//
//    @GetMapping("/")
//    private List<Post> findAll() {
//        return repozitori.findAll();
//    }
//
//    @GetMapping("/id/{id}")
//    Optional<Post> findById(@PathVariable String id) {
//        return repozitori.findById(id);
//    }
//
//    @PostMapping("/test")
//    public List<Post> init() {
//        List<Post> posts = cliebt.getAllPosts();
//
//        // Используем обычный цикл for с индексом
//        for(Post post: posts){
//            post.setAuthors(authors);
//        }
//
//        // Сохраняем все посты в репозитории
//        repozitori.saveAll(posts);
//
//        // Возвращаем список постов
//        return posts;
//
//    }
//
//    @PostMapping("/add")
//    public Post addPost(@RequestBody Post post){
//        return repozitori.save(post);
//    }
//
//    @PutMapping("/put/post/{id}/{newPost}")
//    public Post put(@PathVariable String id, @RequestBody Post newPost){
//        Post post=repozitori.findById(id).orElse(null);
//        if(post==null){
//            return null;
//        }
//        post=newPost;
//        return repozitori.save(post);
//    }
//
//    @PutMapping("/put/body/{id}/{body}")
//    public Post put(@PathVariable String id, @PathVariable String body){
//        Post post=repozitori.findById(id).orElse(null);
//        if(post==null){
//            return null;
//        }
//        post.setBody(body);
//
//        return repozitori.save(post);
//    }
//
//    @DeleteMapping("/deleteAll")
//    public void deleteAll() {
//        repozitori.deleteAll();
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public void deleteById(@PathVariable String id){
//        repozitori.deleteById(id);
//    }
//
//}

//import java.util.Optional;
//
//@RestController
//@RequestMapping("api/demo/posts")
//public class PostControler {
//    private final PostRepository repozitori;
//    private final JsonPlaceHolder cliebt;
//    private final List<Author> authors;
//