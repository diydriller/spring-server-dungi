package com.dungi.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.dungi.core",
        "com.dungi.common"
})
@SpringBootApplication
public class CoreConfiguration {
}
