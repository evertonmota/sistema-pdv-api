package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.dto.TokenDTO;
import com.sistemas.pdv.record.LoginRecord;
import com.sistemas.pdv.security.JwtService;
import com.sistemas.pdv.service.CustomUserDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtService jwtService;

    @Value("${security.jwt.expiration}")
    private String expiration;

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody LoginRecord loginData){
        try {
            // Verificar se as credenciais sao validas.
            userDetailService.verifyUserCredencials(loginData);
            String token = jwtService.generateToken(loginData.username());
            // Gerar o token
            return new ResponseEntity(new TokenDTO(token, expiration), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
