package com.example.demo.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }

    public void saveSessionData(String key, Object value) {
       try {
           session.setAttribute(key, value);
       }catch (Exception e) {
           throw new RuntimeException("SessionService::saveSessionData-> " +e);
       }


    }
    public Object getSessionData(String key) {
        try {
            return session.getAttribute(key);
        }catch (Exception e) {
            throw new RuntimeException("SessionService::getSessionData-> " +e);
        }
    }

    public void deleteSession(){
        try {
            session.invalidate();
        }catch (Exception e) {
            throw new RuntimeException("SessionService::deleteSession-> " +e);
        }
    }
    public String getSessionId(){
        return session.getId();
    }




}
