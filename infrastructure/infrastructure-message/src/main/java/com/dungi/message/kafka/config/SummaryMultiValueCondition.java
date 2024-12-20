package com.dungi.message.kafka.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SummaryMultiValueCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment env = conditionContext.getEnvironment();
        String kind = env.getProperty("message.kind");
        String owner = env.getProperty("message.owner");

        return "kafka".equals(kind) && "api".equals(owner);
    }
}
