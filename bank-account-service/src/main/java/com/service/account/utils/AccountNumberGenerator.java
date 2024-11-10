package com.service.account.utils;

import com.service.account.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    @Autowired
    private AccountRepo accountRepo;

    private static final int ACCOUNT_NUMBER_LENGTH = 12;
    private static final SecureRandom random = new SecureRandom();

    public long generateAccountNumber(){
        long accountNumber = generateRandomAccountNumber();
        while (!isUniqueAccountNumber(accountNumber)){
            accountNumber = generateRandomAccountNumber();
        }
        return accountNumber;
    }

    private long generateRandomAccountNumber() {
        long lowerLimit = (long) Math.pow(10, ACCOUNT_NUMBER_LENGTH - 1);
        long upperLimit = (long) Math.pow(10, ACCOUNT_NUMBER_LENGTH);
        return lowerLimit + (long) (random.nextDouble() * (upperLimit - lowerLimit));
    }

    private boolean isUniqueAccountNumber(long accountNumber){
        return accountRepo.findByAccountNumber(accountNumber).isEmpty();
    }

}
