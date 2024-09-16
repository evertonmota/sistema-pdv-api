package com.sistemas.pdv.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false )
    @NotBlank(message = "O campo descrição é obrigatório.")
    private String name;
    @Column
    @NotBlank(message = "O campo preço é obrigatório.")
    private BigDecimal price;
    @Column
    @NotBlank(message = "O campo quantidade é obrigatório.")
    private int quantity;

}
