package com.sistemas.pdv.controller;

import com.sistemas.pdv.entity.Product;
import com.sistemas.pdv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductRepository productRepository;
    public ProductController(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping()
    public ResponseEntity getAll(){
        // public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity save(@RequestBody Product product  ){
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity edit(@RequestBody Product p){
        try{
            return new ResponseEntity<>(productRepository.save(p), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Produto removido com sucesso.", HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
