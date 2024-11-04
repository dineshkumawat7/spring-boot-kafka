package com.kafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageListeners {
    private final String TOPIC = "test";

    @KafkaListener(topics = TOPIC, groupId = "test-group")
    public void consumeKafkaMessage(String message){
        log.info("consume kafka test topic message: {}", message);
    }
}
