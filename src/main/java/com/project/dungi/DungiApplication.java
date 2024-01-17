package com.project.dungi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DungiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DungiApplication.class, args);
    }
}
