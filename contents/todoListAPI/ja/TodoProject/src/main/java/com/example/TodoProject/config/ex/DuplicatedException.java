package com.example.TodoProject.config.ex;
/*
    회원가입 등에서 사용자 정보를 저장하기 전에
    사용자가 입력한 RequestClientDto 에서 이미 데이터베이스에 저장된 정보 중 겹쳐선 안 되는 정보(ex. 아이디)가 겹치는 경우
    이 예외를 던진다.
 */


public class DuplicatedException extends RuntimeException {
    public DuplicatedException(String m) {
        super(m);
    }
}
