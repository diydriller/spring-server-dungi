package com.dungi.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(basePackages = {
        "com.dungi.apiserver",
        "com.dungi.core",
        "com.dungi.jpa",
        "com.dungi.redis",
        "com.dungi.file",
        "com.dungi.sms",
        "com.dungi.sns"
})
@EnableAsync
@SpringBootApplication
public class ApiServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }
}
