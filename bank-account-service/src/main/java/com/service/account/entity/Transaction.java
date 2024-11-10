package com.service.account.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.account.enums.TransactionStatus;
import com.service.account.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction{
    private String id;
    private String transactionId;
    private Double amount;
    private String userId;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
