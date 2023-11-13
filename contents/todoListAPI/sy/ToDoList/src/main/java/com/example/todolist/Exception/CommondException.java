package com.example.todolist.Exception;

import lombok.Getter;

@Getter
public class CommondException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CommondException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
