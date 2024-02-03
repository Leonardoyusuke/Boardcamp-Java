package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalsDTO {
    
    @NotNull
    private Long customerId;

    @NotNull
    private Long gameId;
    
    @NotNull
    @Min(value = 1)
    private int daysRented;
    
    
}
