package com.dungi.core.infrastructure.message.common;

import java.util.Map;

public interface MessagePublisher {
    void publish(Object message, Map<String, Object> options);
}
