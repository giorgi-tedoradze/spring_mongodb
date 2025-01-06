package com.example.demo.configuration;

import lombok.Setter;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Setter
@Configuration
public class RabbitMQ {
    @Value("${queue.email.name:noname }")
    private String queueName;

   /* @Value("${spring.rabbitmq.username : guest}")
    private String username;

    @Value("${spring.rabbitmq.password : guest}")
    private String password;*/


    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

   /* @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin admin=new RabbitAdmin(connectionFactory());
        admin.declareQueue(queue());//todo ეს არა სწორი წესით ბინი უნდა მარტო ქმნიდეს ობიექტს
        // მაგრამ ჯერ მეზარება
        return admin;
    }*/



}
