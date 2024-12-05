package com.example.demo.Service;

import lombok.Getter;



@Getter
public class OptData {
    private final String opt;
    private final long timestamp;
    public OptData(String opt) {
        this.opt = opt;
        this.timestamp =System.currentTimeMillis();
    }

}
