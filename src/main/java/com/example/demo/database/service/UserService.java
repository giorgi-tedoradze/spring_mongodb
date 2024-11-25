package com.example.demo.database.service;

import com.example.demo.database.repository.UserRepository;
import com.example.demo.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return user.get();
    }

    public Optional<User> findByUsername(String request) {
        return repository.findByUsername(request);
    }

    public void save(User user) {
        repository.save(user);
    }
}