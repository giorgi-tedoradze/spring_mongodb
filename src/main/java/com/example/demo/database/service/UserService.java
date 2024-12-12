package com.example.demo.database.service;

import com.example.demo.database.model.User;
import com.example.demo.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return user.get();
    }

    public Optional<User> findByUsername(String request) {
        Optional<User> user = repository.findByUsername(request);
       /* if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + request + " not found");
        }რეგისტრაცია უბერავს ამის გამო რადგანაც თუ სახელი უკვე არის არ არეგისტრირებს თუ კი არ არის
         ეს შემოწმება შეცდომას აგდებს და გამოდის რომ არანაირში არ რეგისტრილდება
         ადგილზე ამოწმეთ!!!*/
        /*if(user.get().getRole()==null) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n"+"UserDetailsService:: findByUserna:me"+user.get().getRole());
        }*/
        return user;

    }

    public void save(User user) {
        repository.save(user);
    }
}