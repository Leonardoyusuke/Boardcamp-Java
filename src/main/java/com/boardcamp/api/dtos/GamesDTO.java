package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GamesDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    @Min(value = 1)
    private int stockTotal;

    @NotNull
    @Min(value = 1)
    private int pricePerDay;


}
