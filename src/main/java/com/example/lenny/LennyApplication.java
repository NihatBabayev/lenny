package com.example.lenny;

import com.example.lenny.entity.Product;
import com.example.lenny.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
