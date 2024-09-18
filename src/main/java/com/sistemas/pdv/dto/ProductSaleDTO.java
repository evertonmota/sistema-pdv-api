package com.sistemas.pdv.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaleDTO {
    @NotBlank(message = "O Item da venda é obrigatório.")

    private Long productId;
    @NotBlank(message = "O campo quantidade  é obrigatório.")
    private int quantity;

}
