package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.dto.UserDTO;
import com.sistemas.pdv.entity.User;
import com.sistemas.pdv.exceptions.NoItemException;
import com.sistemas.pdv.service.UserService;
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
    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody User user){
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }catch(Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping()
    public ResponseEntity edit(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        }catch (NoItemException error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete (@PathVariable Long id){
        try{
            userService.deleteById(id);
             return new ResponseEntity(new ResponseDTO("Usuario removido com sucess"), HttpStatus.OK);
        }
        catch(EmptyResultDataAccessException error){
            return new ResponseEntity(new ResponseDTO("Nao foi possível localizar o usuário."), HttpStatus.BAD_REQUEST);
            //return new ResponseEntity<>(new ResponseDTO("Nao foi possível localizar o usuário."), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        catch(Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
