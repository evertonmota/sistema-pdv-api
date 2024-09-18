package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.UserDTO;
import com.sistemas.pdv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class signUpController {

    private UserService userService;

    public signUpController(@Autowired UserService userService){
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity create(@Valid @RequestBody UserDTO user){
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }catch(Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
