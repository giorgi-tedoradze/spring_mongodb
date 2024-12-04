package com.example.demo.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/right/user")
public class RightsUserController {

    @GetMapping("/login")
    public ResponseEntity<?> loginUser() {
        return new ResponseEntity<>("User content", HttpStatus.OK);
    }
}
