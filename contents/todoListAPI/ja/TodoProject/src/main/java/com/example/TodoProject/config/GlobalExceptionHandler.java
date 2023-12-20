package com.example.TodoProject.config;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.config.ex.*;
import com.example.TodoProject.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DuplicatedException.class)
    public ResponseEntity<CommonResponseDto> handleDuplicatedException(DuplicatedException duplicatedException, WebRequest webRequest) {
        log.error(duplicatedException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, duplicatedException.getMessage(), webRequest.getDescription(false)));
    }

    @ExceptionHandler(value = NotFoundElementException.class)
    public ResponseEntity<CommonResponseDto> handleNotFoundException(NotFoundElementException notFoundElementException, WebRequest request) {
        log.error(notFoundElementException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, notFoundElementException.getMessage(), request.getDescription(false)));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponseDto(CommonResponse.FAIL, "유효성 검사 실패", errors));
    }

    @ExceptionHandler(value = LoginErrorException.class)
    public ResponseEntity<CommonResponseDto> handleLoginError(LoginErrorException loginErrorException, WebRequest request) {
        log.error(loginErrorException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, loginErrorException.getMessage(), request.getDescription(false)));
    }

}
