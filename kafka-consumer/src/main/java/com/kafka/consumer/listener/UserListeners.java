package com.kafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserListeners {
    private final String TOPIC = "users";
    private final String GROUP_ID = "users-group";

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consumeUser(Object user){
        log.info("consume user from kafka: {}", user);
    }
}
