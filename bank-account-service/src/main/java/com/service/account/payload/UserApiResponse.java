package com.service.account.payload;

import com.service.account.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {
    private LocalDateTime timestamp;
    private boolean success;
    private Integer status;
    private String message;
    private User data;
}
