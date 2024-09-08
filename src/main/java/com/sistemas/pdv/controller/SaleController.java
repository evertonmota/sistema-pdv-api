package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.SaleDTO;
import com.sistemas.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/sale")
public class SaleController {
    private SaleService saleService;

    public SaleController(@Autowired SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping()
    public ResponseEntity post (@RequestBody SaleDTO dto){
        try {
            Long id = saleService.save(dto);
            return new ResponseEntity<>("Venda realizada com sucesso" + id, HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
