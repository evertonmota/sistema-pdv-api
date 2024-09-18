package com.sistemas.pdv.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long id;
    @NotBlank(message = "O campo descrição é obrigatório.")
    private String description;
    @NotBlank(message = "O campo preço é obrigatório.")
    private BigDecimal price;
    @NotBlank(message = "O campo quantidade é obrigatório.")
    private int quantity;
}
