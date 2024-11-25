package com.dungi.message.sqs.publisher;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.dungi.common.exception.ServerErrorException;
import com.dungi.core.infrastructure.message.common.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConditionalOnProperty(name = "message.kind", havingValue = "sqs")
@RequiredArgsConstructor
@Component
public class SqsSender implements MessagePublisher {
    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;

    @Value("${cloud.aws.sqs.queue.url}")
    private String queueUrl;


    @Override
    public void publish(Object message, Map<String, Object> options) {
        var type = (String) options.get("type");
        String messageBody;
        try {
            messageBody = objectMapper.writeValueAsString(
                    Map.of("type", type, "message", message)
            );
        } catch (JsonProcessingException ex) {
            throw new ServerErrorException(ex.getMessage());
        }

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody);
        amazonSQS.sendMessage(request);
    }
}
