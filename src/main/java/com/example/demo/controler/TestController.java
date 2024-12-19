package com.example.demo.controler;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.UserRegistrationInformation;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import com.example.demo.twoFactorAuthentication.EmailService;
import com.example.demo.twoFactorAuthentication.opt.OptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {
    private TokenDriver td;
    private EmailService emailService;
    private OptService optService;
    private AuthenticationService authenticationService;
    private UserRegistrationInformation userRegistrationInformation;

    @GetMapping(value = "/role")
    public ResponseEntity<String> test(Principal principal) {
        try {
            return ResponseEntity.ok(  principal.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/vork")
    public ResponseEntity<?> vork() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<?> email() {
        String opt=optService.optGenerate("tedoradze2004@gmail.com");
        emailService.sendEmail(
                "tedoradze2004@gmail.com",
                "testi",
                "თქვენი ერთჯერადი კოდი:"+opt
        );
        return ResponseEntity.ok(opt);
    }

    @GetMapping(value = "/opt")
    public ResponseEntity<?> testOpt(@RequestParam(defaultValue = "***") String opt) {
        System.out.println(opt);
       boolean validateOpt= optService.validateOpt("tedoradze2004@gmail.com",opt) ;
       System.out.println(validateOpt);
       if(validateOpt){
           return ResponseEntity.ok(HttpStatus.OK);
       }
       return ResponseEntity.badRequest().body(opt);
    }
    @GetMapping("/chash")
    public ResponseEntity<?> chash(@RequestParam String key) {
        return ResponseEntity.ok(userRegistrationInformation.getUserRegistrationInformation(key));
    }
   
}
