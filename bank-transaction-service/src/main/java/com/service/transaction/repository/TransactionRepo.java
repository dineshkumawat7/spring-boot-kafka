package com.service.transaction.repository;

import com.service.transaction.entity.Transaction;
import com.service.transaction.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByTransactionStatus(TransactionStatus transactionStatus);
    Optional<Transaction> findByAccountNumber(Long accountNumber);
    Optional<Transaction> findByTransactionId(String transactionId);
}
