package com.service.transaction.payload;

import com.service.transaction.entity.Account;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountApiResponse {
    private LocalDateTime timestamp;
    private boolean success;
    private Integer status;
    private String message;
    private Account data;
}
