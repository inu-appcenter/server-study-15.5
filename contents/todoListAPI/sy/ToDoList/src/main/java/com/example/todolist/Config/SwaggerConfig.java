package com.example.todolist.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableOpenApi
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){

        Server serverLocal = new Server("local", "http://localhost:8080", "for local usages", Collections.emptyList(), Collections.emptyList());
        Server testServer = new Server("test", "https://seyong.na2ru2.me", "for testing", Collections.emptyList(), Collections.emptyList());

        return new Docket(DocumentationType.OAS_30)
                .servers(serverLocal, testServer)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.todolist.Controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("TodoList Api 명세")
                .description("ToDoList Api 명세서입니다.")
                .version("v1")
                .build();
    }
}
