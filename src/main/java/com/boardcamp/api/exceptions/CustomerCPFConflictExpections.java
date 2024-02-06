package com.boardcamp.api.exceptions;

public class CustomerCPFConflictExpections extends RuntimeException{
    public CustomerCPFConflictExpections(String message){
        super(message);
    }
}
