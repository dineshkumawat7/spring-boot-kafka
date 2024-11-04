package com.service.account.service;

import com.service.account.entity.Account;
import com.service.account.payload.AccountCreateDto;

public interface AccountService {
    Account createAccount(AccountCreateDto accountCreateDto);
}
