package com.boardcamp.api.exceptions;

public class GameAlreadyExistExpections extends RuntimeException{
    public GameAlreadyExistExpections(String message){
        super(message);
    }
}
