package com.service.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.account.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Account implements Serializable {
    @Id
    private String id;
    @Column(name = "account_number", unique = true, nullable = false, length = 11)
    private Long accountNumber;
    private String accountHolderName;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Double balance;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private boolean isActive;
}
