package com.example.demo.model;

public enum Role {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String description;

    // Конструктор перечисления
    Role(String description) {
        this.description = description;
    }

    // Геттер для получения описания
    public String getDescription() {
        return description;
    }
}