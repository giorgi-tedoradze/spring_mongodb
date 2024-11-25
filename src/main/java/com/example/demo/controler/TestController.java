package com.example.demo.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/demo")
public class TestController {
    @GetMapping(value = "/")
    public ResponseEntity<String> test() {
        try {
            return ResponseEntity.ok("მუშაობს");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
