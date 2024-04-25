package com.dungi.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {
        "application-core.yml",
        "application-core-${spring.profiles.active}.yml",
        "application-file.yml",
        "application-sms.yml",
        "application-sns.yml"
    }, factory = YamlLoadFactory.class)
public class PropertiesConfig {
}
