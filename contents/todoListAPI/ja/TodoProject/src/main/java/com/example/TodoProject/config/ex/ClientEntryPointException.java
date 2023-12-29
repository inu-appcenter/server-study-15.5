package com.example.TodoProject.config.ex;

import com.example.TodoProject.common.CommonResponse;
import com.example.TodoProject.dto.CommonResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ClientEntryPointException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("[ClientEntryPointException]인증 실패");

        CommonResponseDto commonResponseDto = new CommonResponseDto(CommonResponse.FAIL, "인증이 실패했습니다.", null);

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));

    }
}
