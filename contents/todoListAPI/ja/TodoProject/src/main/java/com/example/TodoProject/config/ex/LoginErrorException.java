package com.example.TodoProject.config.ex;

public class LoginErrorException extends RuntimeException{
    public LoginErrorException(String m){
        super(m);
    }
}
