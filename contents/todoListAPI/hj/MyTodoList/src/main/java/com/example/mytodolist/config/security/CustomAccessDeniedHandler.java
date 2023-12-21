package com.example.mytodolist.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//AccessDeniedException 은 엑세스 권한이 없는 리소스에 접근할 경우 발생하는 예외이다. 이 예외를 처리하기 위해
//AccessDeniedHandler 인터페이스가 사용되며, SecurityConfiguration에도 exceptionHandling()메서드를 추가하였다.
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        log.info("[handle] 해당 리소스에 엑세스 할 권한이 없습니다.");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Set a custom error message in the response body
        String errorMessage = "접근 불가능 : 해당 리소스에 접근할 권한이 없습니다.";
        response.getWriter().write(errorMessage);
        response.getWriter().flush();
    }
}
