package com.dungi.core.infrastructure.message.common;

public interface MessagePublisher {
    void publish(String topic, Object message);
}
