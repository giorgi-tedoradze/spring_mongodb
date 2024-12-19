package com.example.demo.Service;

import com.example.demo.model.RegistrationAuthenticationRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
@CacheConfig(cacheNames = "user")
@Component
public class UserRegistrationInformation {
    private final Map<String, RegistrationAuthenticationRequest> userRegistrationInformation = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationInformation.class);

    public void setUserRegistrationInformation(
            String key,
            RegistrationAuthenticationRequest userRegistrationInformation
    ){
        this.userRegistrationInformation.put(
                key,
                userRegistrationInformation
        );
    }

    @Cacheable(key = "#key",unless="#result==null")
    public RegistrationAuthenticationRequest getUserRegistrationInformation(String key) {
        RegistrationAuthenticationRequest value = this.userRegistrationInformation.get(key);
        if (value == null) {
            logger.warn("გასაღები არა სწორი {}",key);
            value = new RegistrationAuthenticationRequest("**", "**", "**");
        }
        return value;
    }

    @CacheEvict(key="#key")
    public void removeUserRegistrationInformation(String key){
        userRegistrationInformation.remove(key);
    }

    @Scheduled(fixedRate = 600000)
    @CacheEvict(allEntries=true)
    public void clearOptStorage() {
        userRegistrationInformation.clear();
    }


}
