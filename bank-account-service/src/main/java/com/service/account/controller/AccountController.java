package com.service.account.controller;

import com.service.account.entity.Account;
import com.service.account.payload.AccountCreateDto;
import com.service.account.payload.AccountUpdateDto;
import com.service.account.payload.ApiResponse;
import com.service.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Account>> createNewAccount(@Valid @RequestBody AccountCreateDto accountCreateDto){
        Account account = accountService.createAccount(accountCreateDto);
        ApiResponse<Account> response = ApiResponse.<Account>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.CREATED.value())
                .message("new account created successfully")
                .data(account)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<ApiResponse<Account>> getAccountById(@PathVariable("accountId") String accountId){
        Account account = accountService.getAccountById(accountId);
        ApiResponse<Account> response = ApiResponse.<Account>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK.value())
                .message("account found successfully")
                .data(account)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<Account>>> getPaginatedAccounts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize){
        Page<Account> accounts = accountService.getPaginatedAccounts(pageNumber, pageSize);
        ApiResponse<Page<Account>> response = ApiResponse.<Page<Account>>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK.value())
                .message("accounts found successfully")
                .data(accounts)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<ApiResponse<Account>> updateAccount(
            @PathVariable("accountId") String accountId,
            @Valid @RequestBody AccountUpdateDto accountUpdateDto){
        Account account = accountService.updateAccount(accountId, accountUpdateDto);
        ApiResponse<Account> response = ApiResponse.<Account>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK.value())
                .message("account updated successfully")
                .data(account)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<ApiResponse<Account>> deleteAccountById(@PathVariable("accountId") String accountId){
        accountService.deleteAccount(accountId);
        ApiResponse<Account> response = ApiResponse.<Account>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK.value())
                .message("account deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
