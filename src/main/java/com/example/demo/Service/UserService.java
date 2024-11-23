package com.example.demo.Service;

import com.example.demo.*;
import com.example.demo.exception.UserException;
import com.example.demo.reoisitory.UserRepository;
import com.example.demo.subject.User;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    private final UserRepository userRepository;
    private final TokenDriver tokenDriver;
    @Autowired
    UserService(UserRepository userRepository,TokenDriver tokenDriver){
        this.userRepository = userRepository;
        this.tokenDriver = tokenDriver;
    }


    public void register(AuthenticationRequest request) {
        User user = new User();
        if(userRepository.findByUsername(request.getUsername())!=null){
            throw new UserException("ასეთი მომხმარებელი უკვე არის");
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        userRepository.save(user);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && request.getPassword().equals(user.getPassword())) {
            return authenticate(request.getUsername());
        }

        throw new UsernameNotFoundException("არა სწორი მომხმარებლის სახელი");
    }

    public AuthenticationResponse authenticate(String userName) {
        try {
            TokenCreator accessToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
            TokenCreator refreshToken = tokenDriver.getTokenCreatorByType(TokenType.JWT_REFRESH_TOKEN);
            return new AuthenticationResponse(accessToken.getToken(userName), refreshToken.getToken(userName));

        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
