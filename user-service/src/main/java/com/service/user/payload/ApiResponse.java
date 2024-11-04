package com.service.user.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private HttpStatus status;
    private String message;
    private T data;
}
