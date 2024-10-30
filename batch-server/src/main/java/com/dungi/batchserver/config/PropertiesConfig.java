package com.dungi.batchserver.config;

import com.dungi.common.config.YamlLoadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {
        "application-core.yml"
}, factory = YamlLoadFactory.class)
public class PropertiesConfig {
}
