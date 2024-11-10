package com.service.user.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.user.enums.TransactionStatus;
import com.service.user.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private String id;
    private String transactionId;
    private Double amount;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
