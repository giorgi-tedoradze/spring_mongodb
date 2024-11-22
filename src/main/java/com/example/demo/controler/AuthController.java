package com.example.demo.controler;

import com.example.demo.AuthenticationRequest;
import com.example.demo.AuthenticationResponse;
import com.example.demo.Service.UserService;
import com.example.demo.token.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/jwt")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenDriver tokenDriver;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest request) {
        userService.register(request);
        TokenCreator refreshToken;
        TokenCreator accessToken;
        try {
            refreshToken=tokenDriver.getTokenCreatorByType(TokenType.JWT_REFRESH_TOKEN) ;
            accessToken=tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
            return new ResponseEntity<>(new AuthenticationResponse(accessToken.getToken(request.getUsername()),
                    refreshToken.getToken(request.getUsername())),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        userService.login(request);
        TokenCreator refreshToken;
        TokenCreator accessToken;
        try {
            refreshToken=tokenDriver.getTokenCreatorByType(TokenType.JWT_REFRESH_TOKEN);
            accessToken=tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
            return new ResponseEntity<>(new AuthenticationResponse(accessToken.getToken(request.getUsername()),refreshToken.getToken(request.getUsername()))
                     ,HttpStatus.OK);
        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
