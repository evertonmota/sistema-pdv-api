package com.sistemas.pdv.dto;

import com.sistemas.pdv.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private Long  userId;
    private List<ProductDTO> items;
}
