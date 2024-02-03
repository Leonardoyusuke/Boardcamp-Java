package com.boardcamp.api.exceptions;

public class UserNotFoundExpections extends RuntimeException{
    public UserNotFoundExpections(String message){
        super(message);
    }
}
