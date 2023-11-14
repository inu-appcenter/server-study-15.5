package com.example.mytodolist.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.example.mytodolist")
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Map<String,String>> handleException(NoSuchElementException e, HttpServletRequest request){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.error("Advice 내 handleException호출, {},{}", request.getRequestURI(),e.getMessage());

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error type",httpStatus.getReasonPhrase());
        errorMap.put("code","400");
        errorMap.put("message",e.getMessage());

        return new ResponseEntity<>(errorMap,responseHeaders,httpStatus);
    }
}
