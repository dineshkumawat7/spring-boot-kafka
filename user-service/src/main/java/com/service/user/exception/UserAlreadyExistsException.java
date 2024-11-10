package com.service.user.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("user already exists");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
