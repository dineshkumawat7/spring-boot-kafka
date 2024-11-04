package com.service.transaction.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.transaction.enums.TransactionStatus;
import com.service.transaction.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    private String id;
    private String transactionId;
    private Double amount;
    private String userId;
    private String accountNumber;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
