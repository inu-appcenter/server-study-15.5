package com.example.todolist.Exception;

import com.example.todolist.DTO.CommonResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    //@Valid 유효성검사 실패 시
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.error(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST","올바르지 않은 입력값입니다",errors));
    }

    // 커스텀 예외발생 시
    @ExceptionHandler(CommondException.class)
    public ResponseEntity<CommonResponseDTO> commandExceptionHandler(CommondException ex){
        log.error("예외가 발생했습니다. - "+ex.getExceptionCode().getMessage());
        return ResponseEntity
                .status(ex.getExceptionCode().getStatus())
                .body(CommonResponseDTO.of(ex.getExceptionCode().name(),ex.getExceptionCode().getMessage(),null));

    }

    // @PathVariable로 입력받은 값의 타입이 올바르지 않을 때
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CommonResponseDTO> handleException(Exception ex) {
        String message = "서버 내부에 에러가 발생했습니다.";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponseDTO.of("INTERNAL_SERVER_ERROR",message,null));
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String cause = ex.getMessage();
        if(cause.contains("DateTimeParseException")){
            String message = "올바른 날짜형식이 아닙니다";
            log.error(message+":"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST",message,null));
        }

        return ResponseEntity.status(status).body(CommonResponseDTO.of(status.toString(),"원인을 알 수 없는 예외가 발생했습니다.",cause));
    }

    /* 날짜형식이 올바르지 않을 때
    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity<CommonResponseDTO> dateTimeparseExceptionHandler(java.time.format.DateTimeParseException ex){
        String message = "올바른 날짜형식이 아닙니다";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponseDTO.of("BAD_REQUEST",message,null));
    }
    */

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonResponseDTO.of("NOT_FOUND","존재하지 않는 페이지입니다.",null));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "올바른 요청이 아닙니다.";
        log.error(message+":"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(CommonResponseDTO.of("METHOD_NOT_ALLOWED",message,null));
    }
}


