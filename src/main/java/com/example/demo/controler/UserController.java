package com.example.demo.controler;

import com.example.demo.model.*;
import com.example.demo.Service.AuthenticationService;
import com.example.demo.twoFactorAuthentication.EmailService;
import com.example.demo.twoFactorAuthentication.opt.OptData;
import com.example.demo.twoFactorAuthentication.opt.OptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/freeWay")
public class UserController {
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final OptService optService;

    @PostMapping("/register/user")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationAuthenticationRequest request) {
        authenticationService.registerUser(request);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(new TokenAuthenticationData(request.getUsername(),
                List.of(Role.USER.name()))),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/user/mesije")
    public ResponseEntity<?> sendcode(@RequestBody RegistrationAuthenticationRequest request,
                         HttpServletResponse response) {
        emailService.sendEmail(
                request.getEmail(),
                "code",
                optService.optGenerate(request.getEmail())
        );
        authenticationService.filingHttpHeader(response,request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @PostMapping("/register/user/chekkod")
    public ResponseEntity<?> chekkod(@RequestParam(defaultValue="**")String opt,
                                     HttpServletRequest request){
        RegistrationAuthenticationRequest registrationAuthenticationRequest= authenticationService.extractHttpHeader(request);
        optService.validateOpt(registrationAuthenticationRequest.getEmail(), opt);

        authenticationService.registerUser(registrationAuthenticationRequest);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(new TokenAuthenticationData(registrationAuthenticationRequest.getUsername(),
                List.of(Role.USER.name()))),
                HttpStatus.ACCEPTED);

    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegistrationAuthenticationRequest request) {
        authenticationService.registerAdmin(request);
        return new ResponseEntity<>(authenticationService.createAuthenticationResponse(new TokenAuthenticationData(request.getUsername(),
                List.of(Role.ADMIN.name()))),
                HttpStatus.ACCEPTED);
    }



}