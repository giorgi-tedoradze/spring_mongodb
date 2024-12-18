package com.example.demo.session.sessionModel;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)// Используем прокси для поддержки сериализации
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class") // Указывает, что при сериализации будет добавляться информация о классе
public class UserSession {
    private String username;
    List<String> roles;
}
