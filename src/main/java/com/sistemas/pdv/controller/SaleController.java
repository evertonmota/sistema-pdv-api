package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.dto.SaleDTO;
import com.sistemas.pdv.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/sale")
public class SaleController {
    private SaleService saleService;
    public SaleController(@Autowired SaleService saleService) {
        this.saleService = saleService;
    }
    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>( saleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable long id){
        try{
            return new ResponseEntity<>(saleService.findByID(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping()
    public ResponseEntity post (@Valid  @RequestBody SaleDTO dto){
        try {
            saleService.save(dto);
            return new ResponseEntity<>(new ResponseDTO("Venda realizada com sucesso"), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}