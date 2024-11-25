package com.dungi.core.integration.message.common;

import java.util.Map;

public interface MessagePublisher {
    void publish(Object message, Map<String, Object> options);
}
