package com.service.transaction.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionProcessDto {
    private Double amount;
    private String userId;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
}
