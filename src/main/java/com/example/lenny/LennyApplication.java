package com.example.lenny;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
public class LennyApplication  {
    public static void main(String[] args) {
        SpringApplication.run(LennyApplication.class, args);
    }
}
