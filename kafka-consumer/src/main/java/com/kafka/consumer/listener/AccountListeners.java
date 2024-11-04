package com.kafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountListeners {
    private final String TOPIC = "accounts";
    private final String GROUP_ID = "accounts-group";

    public void consumeAccounts(Object account){
        log.info("consume account from kafka: {}", account);
    }
}
