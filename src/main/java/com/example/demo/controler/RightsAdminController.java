package com.example.demo.controler;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/right/admin")
public class RightsAdminController {

    @GetMapping("/login")
    public ResponseEntity<?> loginAdmin(HttpSession session) {
        return new ResponseEntity<>("Admin content", HttpStatus.OK);
    }
}
