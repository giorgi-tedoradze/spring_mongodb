package com.example.demo.controler;

import com.example.demo.model.LoginAuthenticationRequest;
import com.example.demo.model.RegistrationAuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationAuthenticationRequest request) {
        authenticationService.register(request);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(request.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginAuthenticationRequest loginAuthenticationRequest) {
        return new ResponseEntity<>(authenticationService.login(loginAuthenticationRequest), HttpStatus.OK);
    }
}