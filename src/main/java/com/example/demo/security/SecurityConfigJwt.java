package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigJwt {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключение CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/demo/jwt/login", "/demo/jwt/register").permitAll() // Доступ к /auth/login и /auth/register для всех
                        .anyRequest()
                        .authenticated() // Все остальные запросы требуют аутентификации
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Добавляем JwtRequestFilter перед UsernamePasswordAuthenticationFilter

        return http.build();
    }
}
