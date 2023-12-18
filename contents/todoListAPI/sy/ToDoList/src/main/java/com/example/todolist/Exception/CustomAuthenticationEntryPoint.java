package com.example.todolist.Exception;

import com.example.todolist.DTO.CommonResponseDTO;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(401);
        Gson gson = new Gson();

        CommonResponseDTO commonResponseDTO = CommonResponseDTO.of("HttpStatus.UNAUTHORIZED","인증에 실패하였습니다",null);

        response.getWriter().write(gson.toJson(commonResponseDTO));
    }
}
