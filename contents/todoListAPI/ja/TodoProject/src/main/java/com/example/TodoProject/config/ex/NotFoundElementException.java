package com.example.TodoProject.config.ex;
/**
    Jpa에서 정보를 찾을 때(ex. 사용자 id로 검색, Todo id로 검색...)
    데이터베이스에서 해당 유저 혹은 투두 정보를 찾을 수 없을 때 던지는 예외.
    주의사항은 검색 기능에서 검색하는 단어(keyword)가 포함된 것이 없을 때는 예외를 던지지 않고 exception을 던짐.

    @author 김정아
 */
public class NotFoundElementException extends RuntimeException {

    public NotFoundElementException(String m){
        super(m);
    }

    public NotFoundElementException(String m, Throwable cause){
        super(m, cause);
    }
}
