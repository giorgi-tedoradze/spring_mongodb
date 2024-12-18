package com.example.demo.Service;

import com.example.demo.database.model.User;
import com.example.demo.database.service.UserService;
import com.example.demo.exception.UserException;
import com.example.demo.model.*;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import com.example.demo.twoFactorAuthentication.EmailService;
import com.example.demo.twoFactorAuthentication.opt.OptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenDriver tokenDriver;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OptService optService;
    private final Map<String,RegistrationAuthenticationRequest> UserRegistrationInformation   = new HashMap<>();

    public void register(RegistrationAuthenticationRequest request,List<String> role,String opt) {
        if(!checkRequestOpt(opt,request.getEmail())){
            throw new UserException("კოდი არ არის სწორი");
        }
        User user = new User();

        if (userService.findByUsername(request.getUsername()).isPresent()) {
            throw new UserException("Username is already in use");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userService.save(user);
    }

    public void registerUser(RegistrationAuthenticationRequest request,String opt) {
        register(request, List.of(Role.USER.name()),opt);
    }

    public void registerAdmin(RegistrationAuthenticationRequest request,String opt) {
        register(request,List.of(Role.ADMIN.name()),opt);
    }

    public void saveUserRegistrationInformation(
            String key,
            RegistrationAuthenticationRequest userRegistrationInformation
    ){
        UserRegistrationInformation.put(
                key,
                userRegistrationInformation
        );
    }

    public void removeUserRegistrationInformation(String key){
        UserRegistrationInformation.remove(key);

    }

    public void filingHttpHeader(HttpServletResponse response ,
                       RegistrationAuthenticationRequest userRegistrationInformation ) {

        String uniqueAuthKey= getUniqueAuthKey(userRegistrationInformation);

        saveUserRegistrationInformation(
                uniqueAuthKey,
                userRegistrationInformation
        );
        response.setHeader("X-Unique-Auth-Key",uniqueAuthKey);

    }


    public void sendOptToEmail(String to,String opt){
        emailService.sendEmail(to,"code",opt);
    }

    public void primaryAuthentication(HttpServletResponse response ,
                                      RegistrationAuthenticationRequest userRegistrationInformation){
        String to= userRegistrationInformation.getEmail();
        String opt = optService.optGenerate(to);
        sendOptToEmail(to,opt);
        filingHttpHeader(response,userRegistrationInformation);
    }

    public boolean checkRequestOpt(String opt,String Email){
        return optService.validateOpt(opt,Email);
    }


    public RegistrationAuthenticationRequest extractHttpHeader(HttpServletRequest request){
       String key= request.getHeader("X-Unique-Auth-Key");
       RegistrationAuthenticationRequest registrationAuthenticationRequest= UserRegistrationInformation.get(key);
       removeUserRegistrationInformation(key);
       return registrationAuthenticationRequest;
    }



    public String getUniqueAuthKey(RegistrationAuthenticationRequest userRegistrationInformation){
        long time = new Date().getTime();
        return (
                String.valueOf(time)
                        +userRegistrationInformation.getEmail().hashCode()
                        + userRegistrationInformation.getUsername().hashCode()
        ).substring(0, String.valueOf(time).length() + 6);
    }

    public AuthenticationResponse login(LoginAuthenticationRequest request){
        String username = request.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );

        Optional<User> user = userService.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with this username does not exist");
        }

        return createAuthenticationResponse(new TokenAuthenticationData(user.get()));
    }





    public AuthenticationResponse createAuthenticationResponse(TokenAuthenticationData tokenAuthenticationData) {
        try{
            TokenCreator accessToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
            TokenCreator refreshToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_REFRESH_TOKEN);

            return new AuthenticationResponse(accessToken.createToken(tokenAuthenticationData),
                    refreshToken.createToken(tokenAuthenticationData));
        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}