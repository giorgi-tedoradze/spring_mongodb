package com.example.demo.Service;

import com.example.demo.database.model.User;
import com.example.demo.database.service.UserService;
import com.example.demo.exception.UserException;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.LoginAuthenticationRequest;
import com.example.demo.model.RegistrationAuthenticationRequest;
import com.example.demo.model.Role;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final TokenDriver tokenDriver;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, TokenDriver tokenDriver, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenDriver = tokenDriver;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegistrationAuthenticationRequest request) {
        User user = new User();

        if (userService.findByUsername(request.getUsername()).isPresent()) {
            throw new UserException("ასეთი მომხმარებელი უკვე არის");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userService.save(user);
    }

    public AuthenticationResponse login(LoginAuthenticationRequest request) {
        String username = request.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.getPassword()
                )
        );

        Optional<User> user = userService.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("არა სწორი მომხმარებლის სახელი");
        }

        return createAuthenticationResponse(username);
    }

    public AuthenticationResponse createAuthenticationResponse(String userName) {
        try {
            TokenCreator accessToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
            TokenCreator refreshToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_REFRESH_TOKEN);

            return new AuthenticationResponse(accessToken.getToken(userName), refreshToken.getToken(userName));
        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}