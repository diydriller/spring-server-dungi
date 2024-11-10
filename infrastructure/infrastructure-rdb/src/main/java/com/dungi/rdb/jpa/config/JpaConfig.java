package com.dungi.rdb.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.dungi.rdb.jpa.repository")
@EntityScan(basePackages = "com.dungi.core.domain")
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
