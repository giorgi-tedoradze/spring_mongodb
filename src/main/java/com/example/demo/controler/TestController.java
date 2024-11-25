package com.example.demo.controler;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/test")
public class TestController {
    @GetMapping(value = "/test")
    public ResponseEntity<String> test(Principal principal) {
        try {
            return ResponseEntity.ok("works " + principal.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
