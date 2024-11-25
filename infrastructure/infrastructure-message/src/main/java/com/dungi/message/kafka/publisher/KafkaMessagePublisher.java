package com.dungi.message.kafka.publisher;

import com.dungi.core.infrastructure.message.common.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConditionalOnProperty(name = "message.kind", havingValue = "kafka")
@RequiredArgsConstructor
@Component
public class KafkaMessagePublisher implements MessagePublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(Object message, Map<String, Object> options) {
        var topic = (String) options.get("topic");
        kafkaTemplate.send(topic, message);
    }
}
