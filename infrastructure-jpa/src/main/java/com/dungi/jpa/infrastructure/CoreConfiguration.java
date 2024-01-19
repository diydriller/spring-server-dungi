package com.dungi.jpa.infrastructure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.dungi.jpa.infrastructure.repository")
@EntityScan(basePackages = "com.dungi.core.domain")
@SpringBootApplication
public class CoreConfiguration {
}
