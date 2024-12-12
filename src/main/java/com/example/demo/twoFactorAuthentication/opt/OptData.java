package com.example.demo.twoFactorAuthentication.opt;

import lombok.Getter;



@Getter
public class OptData {
    private final String opt;
    private final long timestamp;
    private final long OTP_VALIDITY_DURATION=5*60*1000;
    public OptData(String opt) {
        this.opt = opt;
        this.timestamp =System.currentTimeMillis();
    }
    public boolean isExpired(){
        return System.currentTimeMillis()-timestamp > OTP_VALIDITY_DURATION;
    }

}