package com.dungi.rdb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.dungi.rdb.jpa.repository")
@EntityScan(basePackages = "com.dungi.core.domain")
@SpringBootApplication
public class TestConfiguration {
}
