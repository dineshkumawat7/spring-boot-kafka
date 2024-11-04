package com.service.account.entity;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private boolean isValid;
}
