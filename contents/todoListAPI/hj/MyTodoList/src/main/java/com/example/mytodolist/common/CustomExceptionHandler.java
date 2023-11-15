package com.example.mytodolist.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "com.example.mytodolist")
@Slf4j
public class CustomExceptionHandler {


    //두개의 예외 처리를 하기 위해 두개의 클래스, 두개의 핸들러가 꼭 필요한 건 아니다. 하나의 Exception 핸들러에서 예외를 낚아채고,
    //낚아챈 에외를 구벼하여 해당 예외에 대한 내용만 구별하면 된다.
    @ExceptionHandler(value = {NoSuchElementException.class, MethodArgumentNotValidException.class, RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(Exception e, HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.error("Exception: {},{}", request.getRequestURI(), e.getMessage());

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error type", httpStatus.getReasonPhrase());
        errorMap.put("code", String.valueOf(httpStatus.value()));

        if(e instanceof NoSuchElementException){
            errorMap.put("message", "NoSuchElementException 이 발생하였습니다. 자세한 내용은 로그를 확인해주세요.");
        }else if (e instanceof  MethodArgumentNotValidException){
            errorMap.put("message", "MethodArgumentNotValidException 이 발생하였습니다. 자세한 내용은 로그를 확인해주세요. ");
        }else {
            errorMap.put("message", "RuntimeException 이 발생하였습니다. 자세한 내용은 로그를 확인해주세요.");
        }
        return new ResponseEntity<>(errorMap, responseHeaders, httpStatus);
    }

}
