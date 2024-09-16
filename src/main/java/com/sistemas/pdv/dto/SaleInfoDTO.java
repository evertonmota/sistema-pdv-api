package com.sistemas.pdv.dto;

import com.sistemas.pdv.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfoDTO {
    private String user;
    private String data;
    private List<ProductInfoDTO> products;


}
