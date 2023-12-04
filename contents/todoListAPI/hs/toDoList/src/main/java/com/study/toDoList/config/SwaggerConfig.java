package com.study.toDoList.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    private Info apiInfo() {
        return new Info()
                .title("toDoList")
                .description("회원테이블 : 이메일, 비밀번호, 닉네임 / 할일테이블 : 생성일, 수정일, 제목, 내용, 종료 날짜, 종료 여부 ")
                .version("1.0.0");
    }
}
