package com.boardcamp.api.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank
    private String name;

    @NotNull
    @Length(min = 11, max = 11)
    private String cpf;

}
