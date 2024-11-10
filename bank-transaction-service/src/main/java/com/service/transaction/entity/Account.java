package com.service.transaction.entity;

import com.service.transaction.enums.AccountType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Account{
    private String id;
    private Long accountNumber;
    private AccountType accountType;
    private Double balance;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
