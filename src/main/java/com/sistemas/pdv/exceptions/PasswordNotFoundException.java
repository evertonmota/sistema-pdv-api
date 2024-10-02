package com.sistemas.pdv.exceptions;

public class PasswordNotFoundException extends RuntimeException {
    public PasswordNotFoundException(String s) {
        super(s);
    }
}
