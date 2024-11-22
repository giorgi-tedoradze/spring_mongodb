package com.example.demo.Service;

import com.example.demo.*;
import com.example.demo.reoisitory.UserRepository;
import com.example.demo.subject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(AuthenticationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        // Сохранение пользователя в базе данных
        userRepository.save(user);
    }

    public boolean login(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && request.getPassword().equals(user.getPassword())) {
            return true;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
