package com.service.account.service;

import com.service.account.entity.Account;
import com.service.account.payload.AccountCreateDto;
import com.service.account.payload.AccountUpdateDto;
import org.springframework.data.domain.Page;

public interface AccountService {
    Account createAccount(AccountCreateDto accountCreateDto);
    Account getAccountById(String accountId);
    Page<Account> getPaginatedAccounts(Integer pageNumber, Integer pageSize);
    Account updateAccount(String accountId, AccountUpdateDto accountUpdateDto);
    boolean deleteAccount(String accountId);
}
