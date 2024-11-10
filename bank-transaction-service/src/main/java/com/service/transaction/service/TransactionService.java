package com.service.transaction.service;

import com.service.transaction.entity.Transaction;
import com.service.transaction.payload.TransactionProcessDto;

public interface TransactionService {
    Transaction processTransaction(TransactionProcessDto transactionProcessDto);
}
