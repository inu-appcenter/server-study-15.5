package com.example.TodoProject.config;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DuplicatedException.class)
    public ResponseEntity<CommonResponseDto> handleDuplicatedException(DuplicatedException duplicatedException, WebRequest webRequest) {
        log.error(duplicatedException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, duplicatedException.getMessage(), webRequest.getDescription(false)));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<CommonResponseDto> handleNotFoundException(NotFoundException notFoundException, WebRequest request) {
        log.error(notFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, notFoundException.getMessage(), request.getDescription(false)));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponseDto(CommonResponse.FAIL, "유효성 검사 실패", errors));
    }
}
