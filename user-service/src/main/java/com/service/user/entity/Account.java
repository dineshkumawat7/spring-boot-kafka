package com.service.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.user.enums.AccountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account{
    private String id;
    private Long accountNumber;
    private AccountType accountType;
    private Double balance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private boolean isActive;
}
