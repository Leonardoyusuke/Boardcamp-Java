package com.boardcamp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({CustomerCPFConflictExpections.class})
    public ResponseEntity<Object> handlerCPFInUse(CustomerCPFConflictExpections exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundExpections.class})
    public ResponseEntity<Object> handlerUserNotFound(UserNotFoundExpections exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({GameAlreadyExistExpections.class})
    public ResponseEntity<Object> handlerGameAlreadyExist(GameAlreadyExistExpections exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler({GameNotFoundExpections.class})
    public ResponseEntity<Object> handlerGameNotFound(GameNotFoundExpections exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler({GameOutOfStockExpections.class})
    public ResponseEntity<Object> handlerGameOutOfStock(GameOutOfStockExpections exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
    @ExceptionHandler({RentalAlreadyFinished.class})
    public ResponseEntity<Object> handleRentalAlreadyFinished(RentalAlreadyFinished exception){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
    @ExceptionHandler({RentalNotFoundExpections.class})
    public ResponseEntity<Object> handleRentalNotFound(RentalNotFoundExpections exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


}
