package com.sistemas.pdv.controller;

import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody User user){
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        }catch(Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity edit(@RequestBody User user){
        try {
            Optional<User> userEdit = userRepository.findById(user.getId());
            if(userEdit.isPresent()){
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }catch(Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete (@PathVariable Long id){
        try{
             userRepository.deleteById(id);
             return new ResponseEntity("Usuario deleteado com sucesso.", HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>(new ResponseDTO("Nao foi possível localizar o usuário."), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        catch(Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
