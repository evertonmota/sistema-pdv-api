package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.dto.SaleDTO;
import com.sistemas.pdv.exceptions.InvalidOperationException;
import com.sistemas.pdv.exceptions.NoItemException;
import com.sistemas.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        }catch (NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping()
    public ResponseEntity post (@RequestBody SaleDTO dto){
        try {
            long id = saleService.save(dto);
            return new ResponseEntity<>(new ResponseDTO("Venda realizada com sucesso"), HttpStatus.CREATED);
        }catch (NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
