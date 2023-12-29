package com.example.mytodolist.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
//인증이 실패한 상황을 처리하는 클래스
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {

        log.info("[commence] 인증에 실패하였습니다 : " + exception.getMessage());

        //HTTP 응답 헤더의 Content-Type을 JSON으로 설정합니다.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //HTTP 응답 상태 코드를 401 Unauthorized로 설정합니다.
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        //try-with-resource 블록을 만들어, 블록을 빠져나가면 os  가 자동으로 닫히게 만듬.,
        //자원 누수를 방지하기 위한 좋은 방법.
        try (OutputStream os = response.getOutputStream()) {
            //Jackson 라이브러리의 ObjectMapper를 사용하여 JSON 형식의 에러 메시지를 생성합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            //에러 메시지를 JSON 형식으로 작성하여 출력합니다.
            objectMapper.writeValue(os,"권한이 존재하지않아 인증에 실패하였습니다.");
            //OutputStream을 비워내고 닫습니다.
            os.flush();
        }
    }
}
