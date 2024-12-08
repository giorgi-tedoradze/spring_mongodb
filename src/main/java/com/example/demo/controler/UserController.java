package com.example.demo.controler;

import com.example.demo.model.*;
import com.example.demo.Service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/freeWay")
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register/user")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationAuthenticationRequest request) {
        authenticationService.registerUser(request);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(new TokenAuthenticationData(request.getUsername(),
                Role.USER)),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegistrationAuthenticationRequest request) {
        authenticationService.registerAdmin(request);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(new TokenAuthenticationData(request.getUsername(),
                Role.ADMIN)),
                HttpStatus.ACCEPTED);
    }



}