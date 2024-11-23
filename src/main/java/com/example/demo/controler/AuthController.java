package com.example.demo.controler;

import com.example.demo.AuthenticationRequest;
import com.example.demo.AuthenticationResponse;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class AuthController {
    private final UserService userService;
    @Autowired
    AuthController ( UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> test(){
        try {
            return ResponseEntity.ok("მუშაობს");
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/jwt/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        userService.register(request);
        return new ResponseEntity<>(userService.authenticate(request.getUsername()),
                HttpStatus.OK);
    }

    @PostMapping("/jwt/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(userService.login(request),
                HttpStatus.OK);
    }


}
