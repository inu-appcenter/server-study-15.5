package com.study.toDoList.exception.ex;

import lombok.Getter;

@Getter
public class MyNotValidException extends RuntimeException{
    private MyErrorCode errorCode;

    public  MyNotValidException(MyErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
