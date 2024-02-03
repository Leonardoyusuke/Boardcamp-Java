package com.boardcamp.api.exceptions;

public class RentalAlreadyFinished extends RuntimeException{
    public RentalAlreadyFinished(String message){
        super(message);
    }
}
