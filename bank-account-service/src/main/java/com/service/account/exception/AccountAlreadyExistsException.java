package com.service.account.exception;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(){
        super("an account already created");
    }
    public AccountAlreadyExistsException(String message){
        super(message);
    }
}
