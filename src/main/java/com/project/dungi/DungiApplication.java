package com.project.dungi;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableBatchProcessing
@SpringBootApplication
public class DungiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DungiApplication.class, args);
    }
}
