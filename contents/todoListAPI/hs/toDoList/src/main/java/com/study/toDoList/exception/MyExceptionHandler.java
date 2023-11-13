package com.study.toDoList.exception;

import com.study.toDoList.dto.ResponseDto;
import com.study.toDoList.exception.ex.MyNotFoundException;
import com.study.toDoList.exception.ex.MyNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class MyExceptionHandler{

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
        return new ResponseEntity<>(new ResponseDto(-1,ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<?> MyNotFoundException(MyNotFoundException ex){
            return new ResponseEntity<>(new ResponseDto(-1,ex.getErrorCode().getMessage()),ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(MyNotValidException.class)
    public ResponseEntity<?> MyNotValidException(MyNotValidException ex){
        return new ResponseEntity<>(new ResponseDto(-1,ex.getErrorCode().getMessage()),ex.getErrorCode().getStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String message = fieldError.getDefaultMessage();
        return new ResponseEntity<>(new ResponseDto(-1,message),HttpStatus.BAD_REQUEST);
    }
}
