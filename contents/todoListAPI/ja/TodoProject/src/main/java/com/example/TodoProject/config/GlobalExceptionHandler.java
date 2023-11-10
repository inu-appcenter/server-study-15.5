package com.example.TodoProject.config;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.config.ex.DuplicatedException;
import com.example.TodoProject.config.ex.NotFoundException;
import com.example.TodoProject.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = DuplicatedException.class)
    public ResponseEntity<CommonResponseDto> DuplicatedException(DuplicatedException duplicatedException) {
        log.error(duplicatedException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, duplicatedException.getMessage(), "null"));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<CommonResponseDto> NotFoundException(NotFoundException notFoundException) {
        log.error(notFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDto(CommonResponse.FAIL, notFoundException.getMessage(), "null"));
    }
}
