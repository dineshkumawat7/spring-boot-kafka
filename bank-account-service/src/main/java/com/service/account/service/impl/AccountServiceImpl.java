package com.service.account.service.impl;

import com.service.account.entity.Account;
import com.service.account.payload.AccountCreateDto;
import com.service.account.repository.AccountRepo;
import com.service.account.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_TOPIC = "accounts";
    private static final String USER_SERVICE_URL = "http://localhost:8080/users/";
    @Autowired
    private KafkaTemplate<String, Account> kafkaTemplate;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Account createAccount(AccountCreateDto accountCreateDto) {
        Account account = Account.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .accountNumber(generateAccountNumber())
                .accountType(accountCreateDto.getAccountType())
                .userId("jwehfjweh")
                .balance(accountCreateDto.getBalance())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        log.info("Published bank account with ID: {} to topic: {}", account.getId(), ACCOUNT_TOPIC);
        return accountRepo.save(account);
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUser")
    public User getUserById(Long id) {
        return restTemplate.getForObject(USER_SERVICE_URL + id, User.class);
    }

    public User fallbackGetUser(Long id, Throwable throwable) {
        // Return a default User object or handle the error as needed
        return new User(); // or null, or throw a custom exception
    }

    private String generateAccountNumber(){
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String numericAccount = uuid.substring(0, Math.min(uuid.length(), 10));
        return "0" + numericAccount;
    }
}
