package com.example.todolist.Exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    USER_NOTFOUND(HttpStatus.UNAUTHORIZED,"존재하지 않는 유저입니다"),

    NOT_MYTODO(HttpStatus.FORBIDDEN,"ToDo에 대한 권한이 없습니다."),
    NOT_MYREPLY(HttpStatus.FORBIDDEN,"Reply에 대한 권한이 없습니다."),

    TODO_NOTFOUND(HttpStatus.NOT_FOUND,"존재하지 않는 ToDo입니다."),
    REPLY_NOTFOUND(HttpStatus.NOT_FOUND,"존재하지 않는 reply입니다."),

    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST,"중복된 이메일입니다."),
    PASSWORD_NOTMATCH(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다"),
    EMOTION_NOTFOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 이모션입니다."),
    ALREADY_EXIST_EMOTION(HttpStatus.BAD_REQUEST,"이미 좋아요를 눌렀습니다");


    private final HttpStatus status;
    private final String message;
    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
