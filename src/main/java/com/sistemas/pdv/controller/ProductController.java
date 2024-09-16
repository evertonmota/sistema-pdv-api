package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ProductDTO;
import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.entity.Product;
import com.sistemas.pdv.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductRepository productRepository;

    private ModelMapper mapper ;
    public ProductController(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
    }
    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ProductDTO product  ){
        return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity edit(@Valid  @RequestBody ProductDTO dto){
        try{
            return new ResponseEntity<>(productRepository.save(mapper.map(dto, Product.class)), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso."), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
