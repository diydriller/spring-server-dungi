package com.project.dungi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DungiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DungiApplication.class, args);
    }
}
