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

@RestControllerAdvice
@Slf4j
public class CustomValidExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.error("Validation Exception: {},{}", request.getRequestURI(), e.getMessage());

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error type", httpStatus.getReasonPhrase());
        errorMap.put("code", String.valueOf(httpStatus.value()));
        errorMap.put("message", "유효성 검사에 실패하였습니다. 올바른 형식의 값을 입력해주세요");

        return new ResponseEntity<>(errorMap, responseHeaders, httpStatus);
    }
}
