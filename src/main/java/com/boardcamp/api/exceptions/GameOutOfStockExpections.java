package com.boardcamp.api.exceptions;

public class GameOutOfStockExpections extends RuntimeException{
    public GameOutOfStockExpections(String message) {
        super(message);
    }

}
