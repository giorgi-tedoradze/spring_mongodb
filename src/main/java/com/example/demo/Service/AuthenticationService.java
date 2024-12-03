package com.example.demo.Service;

import com.example.demo.database.model.User;
import com.example.demo.database.service.UserService;
import com.example.demo.exception.UserException;
import com.example.demo.model.*;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenDriver tokenDriver;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void register(RegistrationAuthenticationRequest request,Role role) {
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

    public void registerUser(RegistrationAuthenticationRequest request) {
        register(request,Role.USER);
    }

    public void registerAdmin(RegistrationAuthenticationRequest request) {
        register(request,Role.ADMIN);
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