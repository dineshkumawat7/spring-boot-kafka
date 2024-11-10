package com.service.account.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private Integer status;
    private String message;
    private T data;
}
