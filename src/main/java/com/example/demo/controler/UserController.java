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
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestParam(defaultValue="**")String opt,
                                                               HttpServletRequest requestHttp) {
        RegistrationAuthenticationRequest registrationAuthentication=
                authenticationService.extractHttpHeader(requestHttp);

        authenticationService.registerUser(registrationAuthentication,opt);
        return new ResponseEntity<>(authenticationService.
                createAuthenticationResponse(
                        new TokenAuthenticationData(
                                registrationAuthentication.getUsername(),
                                List.of(Role.USER.name())
                        )
                ),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/register/primaryAuthentication")
    public ResponseEntity<?> sendOpt(@RequestBody RegistrationAuthenticationRequest request,
                         HttpServletResponse response) {

        authenticationService.primaryAuthentication(response,request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestParam(defaultValue="**")String opt,
                                                                HttpServletRequest requestHttp) {
        RegistrationAuthenticationRequest registrationAuthentication=
                authenticationService.extractHttpHeader(requestHttp);

        authenticationService.registerAdmin(registrationAuthentication,opt);
        return new ResponseEntity<>(authenticationService.
                createAuthenticationResponse(
                        new TokenAuthenticationData(
                                registrationAuthentication.getUsername(),
                                List.of(Role.ADMIN.name())
                        )
                ),
                HttpStatus.ACCEPTED);
    }



}