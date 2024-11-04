package com.kafka.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionListeners {
    private final String TRANSACTION_TOPIC = "transaction";
    private final String TRANSACTION_COMPLETE_TOPIC = "transaction_complete";
    private final String TRANSACTION_FAILED_TOPIC = "transaction_failed";
    private final String TRANSACTION_PENDING_TOPIC = "transaction_pending";
    private final String TRANSACTION_PROCESSING_TOPIC = "transaction_processing";
    private final String GROUP_ID = "transaction-group";

    @KafkaListener(topics = TRANSACTION_TOPIC, groupId = GROUP_ID)
    public void consumeTransaction(Object object){
        log.info("consume transaction: {}", object);
    }

    @KafkaListener(topics = TRANSACTION_COMPLETE_TOPIC, groupId = GROUP_ID)
    public void consumeCompleteTransaction(Object object){
        log.info("consume completed transaction: {}", object);
    }

    @KafkaListener(topics = TRANSACTION_FAILED_TOPIC, groupId = GROUP_ID)
    public void consumeFailedTransaction(Object object){
        log.info("consume failed transaction: {}", object);
    }

    @KafkaListener(topics = TRANSACTION_PENDING_TOPIC, groupId = GROUP_ID)
    public void consumePendingTransaction(Object object){
        log.info("consume pending transaction: {}", object);
    }

    @KafkaListener(topics = TRANSACTION_PROCESSING_TOPIC, groupId = GROUP_ID)
    public void consumeProcessingTransaction(Object object){
        log.info("consume processing transaction: {}", object);
    }
}
