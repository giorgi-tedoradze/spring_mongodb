package com.example.demo.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("right/")
public class Rightscontroler {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/login")
    public ResponseEntity<?> getUser() {
        return new ResponseEntity<>("User content", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/admin/login")
    public ResponseEntity<?> getAdmin() {
        return new ResponseEntity<>("Admin content", HttpStatus.OK);
    }
}
