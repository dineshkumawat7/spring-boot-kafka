package com.service.transaction.service.impl;

import com.service.transaction.entity.Account;
import com.service.transaction.entity.Transaction;
import com.service.transaction.entity.User;
import com.service.transaction.enums.TransactionStatus;
import com.service.transaction.enums.TransactionType;
import com.service.transaction.payload.AccountApiResponse;
import com.service.transaction.payload.TransactionProcessDto;
import com.service.transaction.repository.TransactionRepo;
import com.service.transaction.service.TransactionService;
import com.service.transaction.utils.TransactionIdGenerator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;
    private static final String TRANSACTION_TOPIC = "transactions";
    private static final String BASE_URL = "http://localhost:8080";
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Transaction processTransaction(TransactionProcessDto transactionProcessDto) {
        Account senderAccount = getAccount(transactionProcessDto.getSenderAccountNumber());
        Account receiverAccount = getAccount(transactionProcessDto.getReceiverAccountNumber());
        if(senderAccount == null){

        }
        if(senderAccount.getBalance() < transactionProcessDto.getAmount()){
            transactionStatus = TransactionStatus.CANCELED;
            log.error("transaction canceled due to insufficient balance");
        }
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .transactionId(TransactionIdGenerator.generateTransactionId())
                .transactionType(TransactionType.TRANSFER)
                .amount(transactionProcessDto.getAmount())
                .senderAccountNumber(senderAccount.getAccountNumber())
                .receiverAccountNumber(receiverAccount.getAccountNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .userId(senderAccount.getUserId())
                .build();
        return transactionRepo.save(transaction);
    }

    @CircuitBreaker(name = "getUser", fallbackMethod = "getUserFallback")
    public Account getAccount(Long accountNumber) {
        String url = BASE_URL + "/accounts/get/" + accountNumber;
        ResponseEntity<AccountApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AccountApiResponse.class
        );
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            AccountApiResponse apiResponse = response.getBody();
            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            } else {
                throw new RuntimeException("User not found in response");
            }
        } else {
            throw new RuntimeException("Failed to fetch user data");
        }
    }

    public User getUserFallback(String userId, Throwable throwable) {
        log.error("currently user service down due to: {}", throwable.getMessage());
        return new User();
    }
}
