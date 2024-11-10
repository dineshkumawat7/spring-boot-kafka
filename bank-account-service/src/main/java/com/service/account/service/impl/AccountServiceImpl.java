package com.service.account.service.impl;

import com.service.account.entity.Account;
import com.service.account.entity.User;
import com.service.account.exception.AccountAlreadyExistsException;
import com.service.account.exception.ResourceNotFoundException;
import com.service.account.payload.AccountCreateDto;
import com.service.account.payload.AccountUpdateDto;
import com.service.account.payload.UserApiResponse;
import com.service.account.repository.AccountRepo;
import com.service.account.service.AccountService;
import com.service.account.utils.AccountNumberGenerator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_TOPIC = "accounts";
    private static final String BASE_URL = "http://localhost:8080";
    @Autowired
    private KafkaTemplate<String, Account> kafkaTemplate;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AccountNumberGenerator accountNumberGenerator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Account createAccount(AccountCreateDto accountCreateDto) {
        User user = getUserById(accountCreateDto.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("user not found with id: " + accountCreateDto.getUserId());
        }
        if (accountRepo.findByUserId(accountCreateDto.getUserId()).isPresent()) {
            throw new AccountAlreadyExistsException("an account already created with this user");
        }
        Account account = Account.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .accountNumber(accountNumberGenerator.generateAccountNumber())
                .accountHolderName(user.getFirstName() + " " + user.getLastName())
                .accountType(accountCreateDto.getAccountType())
                .balance(accountCreateDto.getBalance())
                .userId(user.getId())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        log.info("Published bank account with ID: {} to topic: {}", account.getId(), ACCOUNT_TOPIC);
        return accountRepo.save(account);
    }

    @Override
    public Account getAccountById(String accountId) {
        return accountRepo.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("account not found with id " + accountId));
    }

    @Override
    public Page<Account> getPaginatedAccounts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return accountRepo.findAll(pageable);
    }

    @Override
    public Account updateAccount(String accountId, AccountUpdateDto accountUpdateDto) {
        return null;
    }

    @Override
    public boolean deleteAccount(String accountId) {
        Optional<Account> optionalAccount = accountRepo.findById(accountId);
        if (optionalAccount.isPresent()) {
            accountRepo.delete(optionalAccount.get());
            return true;
        }
        throw new ResourceNotFoundException("account not found with id " + accountId);
    }

    @CircuitBreaker(name = "getUser", fallbackMethod = "getUserFallback")
    public User getUserById(String userId) {
        String url = BASE_URL + "/users/get/" + userId;
        ResponseEntity<UserApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                UserApiResponse.class
        );
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            UserApiResponse apiResponse = response.getBody();
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
