package com.service.account.exception;

import com.service.account.payload.ErrorResponse;
import com.service.account.payload.FieldValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldValidationErrorResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        FieldValidationErrorResponse<Map<String, String>> response = FieldValidationErrorResponse.<Map<String, String>>builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("validation error")
                .fields(errors)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .error(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExistsException(AccountAlreadyExistsException exception){
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .status(HttpStatus.CONFLICT.value())
                .error(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
