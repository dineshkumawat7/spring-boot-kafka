package com.service.user.controller;

import com.service.user.entity.User;
import com.service.user.payload.ApiResponse;
import com.service.user.payload.UserRegisterDto;
import com.service.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/status")
    public String getStatus() {
        return "Running up user service..";
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        User createdUser = userService.createUser(userRegisterDto);
        ApiResponse<User> response = ApiResponse.<User>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.CREATED)
                .message("new user register successfully")
                .data(createdUser)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        ApiResponse<User> response = ApiResponse.<User>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK)
                .message("user found successfully")
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<Page<User>>> getPaginatedUser(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer sizeNumber) {
        Page<User> users = userService.getPaginatedUser(pageNumber, sizeNumber);
        ApiResponse<Page<User>> response = ApiResponse.<Page<User>>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .status(HttpStatus.OK)
                .message("user found successfully")
                .data(users)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
