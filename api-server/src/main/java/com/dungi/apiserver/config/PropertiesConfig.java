package com.dungi.apiserver.config;

import com.dungi.common.config.YamlLoadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {
        "application-core.yml",
        "application-file.yml",
        "application-sms.yml",
        "application-sns.yml"
    }, factory = YamlLoadFactory.class)
public class PropertiesConfig {
}
