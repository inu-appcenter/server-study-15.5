package com.study.toDoList.exception.ex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저 아이디값이 존재하지 않습니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND,"할일 아이디값이 존재하지 않습니다."),
    USER_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST,"동일한 이메일이 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
