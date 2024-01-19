package com.dungi.batchserver;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.dungi.batchserver",
        "com.dungi.core.domain.todo",
        "com.dungi.core.domain.room",
        "com.dungi.jpa"
})
@EnableBatchProcessing
@SpringBootApplication
public class BatchServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchServerApplication.class, args);
    }
}
