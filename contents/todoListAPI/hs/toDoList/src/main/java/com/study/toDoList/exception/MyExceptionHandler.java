package com.study.toDoList.exception;

import com.study.toDoList.dto.ResponseDto;
import com.study.toDoList.exception.ex.MyDuplicateException;
import com.study.toDoList.exception.ex.MyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler{

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
        log.error("유효성 검사 예외 발생 msg:{}",ex.getMessage());
        return new ResponseEntity<>(new ResponseDto(-1,ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<?> MyNotFoundException(MyNotFoundException ex){
        log.error("존재하지 않는 값 예외 발생 msg:{}",ex.getErrorCode().getMessage());
        return new ResponseEntity<>(new ResponseDto(-1,ex.getErrorCode().getMessage()),ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(MyDuplicateException.class)
    public ResponseEntity<?> MyDuplicateException(MyDuplicateException ex){
        log.error("중복 값 예외 발생 msg:{}",ex.getErrorCode().getMessage());
        return new ResponseEntity<>(new ResponseDto(-1,ex.getErrorCode().getMessage()),ex.getErrorCode().getStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String message = fieldError.getDefaultMessage();
        log.error("유효성 검사 예외 발생 msg:{}",message);
        return new ResponseEntity<>(new ResponseDto(-1,message),HttpStatus.BAD_REQUEST);
    }

}
