package com.example.demo.twoFactorAuthentication.opt;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OptService {
    private final Map<String, OptData> optStorage = new HashMap();

    public String optGenerate(String email) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000);
        optStorage.put(email, new OptData(otp));
        return otp;
    }

    public boolean validateOpt(String email, String opt) {
        OptData optData = optStorage.get(email);
        if (optData == null || optData.isExpired()) {
            optStorage.remove(email);
            return false;
        }

        if (opt.equals(optData.getOpt())) {
            optStorage.remove(email);
            return true;
        } else {
            return false;
        }

    }
}

