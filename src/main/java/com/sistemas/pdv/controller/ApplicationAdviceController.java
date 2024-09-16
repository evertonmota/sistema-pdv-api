package com.sistemas.pdv.controller;

import com.sistemas.pdv.dto.ResponseDTO;
import com.sistemas.pdv.exceptions.InvalidOperationException;
import com.sistemas.pdv.exceptions.NoItemException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(NoItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hadlerNoItemException(NoItemException exception){
        String messageError = exception.getMessage();
        return new ResponseDTO(messageError);
    }
    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hadlerNoItemException(InvalidOperationException exception){
        String messageError = exception.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hadlerEmptyResultDataAccessException(EmptyResultDataAccessException exception){
        String messageError = exception.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hadlerValidExceptions(MethodArgumentNotValidException ex){
        List<String> erros = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach( (error) ->{
            String erroMessage = error.getDefaultMessage();
            erros.add(erroMessage);
        });
        return new ResponseDTO(erros);
    }
}
