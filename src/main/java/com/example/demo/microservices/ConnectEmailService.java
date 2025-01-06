package com.example.demo.microservices;

import com.example.demo.model.EmailMessage;
import com.example.demo.twoFactorAuthentication.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Setter
@Service
public class ConnectEmailService {
    @Value("${queue.email.name:noname }")
    private String queueName;

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(ConnectEmailService.class);

    @Autowired
    ConnectEmailService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(EmailMessage emailMessage) {
        //EmailMessage message = new EmailMessage(email, subject, body);
        // Преобразование в JSON строку
        try {
            String jsonMessage = objectMapper.writeValueAsString(emailMessage);
            // Отправка сообщения
            amqpTemplate.convertAndSend(queueName, jsonMessage);
        }catch (JsonProcessingException e) {
            logger.error("RabbitMQ Error", e);
        }

    }


}
