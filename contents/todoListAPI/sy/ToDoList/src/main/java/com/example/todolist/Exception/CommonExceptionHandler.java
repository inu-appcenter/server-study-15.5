package com.example.todolist.Exception;

import com.example.todolist.DTO.CommonResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponseDTO> validationExceptionHandler(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.error(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST","올바르지 않은 입력값입니다",errors));
    }

    @ExceptionHandler(CommondException.class)
    public ResponseEntity<CommonResponseDTO> commandExceptionHandler(CommondException ex){
        log.error("예외가 발생했습니다. - "+ex.getExceptionCode().getMessage());
        return ResponseEntity
                .status(ex.getExceptionCode().getStatus())
                .body(CommonResponseDTO.of(ex.getExceptionCode().name(),ex.getExceptionCode().getMessage(),null));

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponseDTO> methodArgTypeExceptionHandler(MethodArgumentTypeMismatchException ex){
        Map<String ,String> map= new HashMap<>();

        String fieldName = ex.getName();
        String requiredType = ex.getRequiredType().getSimpleName();
        String message = fieldName+"이 "+requiredType+"타입이여야 합니다.";
        map.put("fieldName",fieldName);
        map.put("requiredType",requiredType);
        log.error("URI값이 올바르지 않습니다. - "+ map.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST",message,map));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResponseDTO> methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex){
        String message = "올바른 요청이 아닙니다.";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonResponseDTO.of("NOT_FOUND",message,null));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CommonResponseDTO> dateTimeparseExceptionHandler(DateTimeParseException ex){
        String message = "올바른 날짜형식이 아닙니다";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST",message,null));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CommonResponseDTO> handleException(Exception ex) {
        String message = "서버 내부에 에러가 발생했습니다.";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponseDTO.of("INTERNAL_SERVER_ERROR",message,null));
    }
}


