package com.example.mytodolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.mytodolist"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(List.of(securityContext())) //API에 대한 보안 컨텍스트를 설정. 보안 컨텍스트는 특정 경로나 작업에 대한 보안 규칙을 정의하는데 사용
                .securitySchemes(List.of(securityScheme())); // 인증에 사용될 보안 스킴을 설정. 보안 스킴은 인증 방식을 나타내는데 사용된다
    }
    private SecurityContext securityContext(){
        //보안 컨텍스트를 설정하는 부분
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    //인증에 사용될 보안 스킴과 인증 범위(스코프)를 설정하는 부분
    private List<SecurityReference> defaultAuth() {
        //인증 범위를 나타내는 클래스
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        //이전에 생성한 authorizationScope 객체를 배열에 넣습니다.
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
        //defaultAuth() 메서드에서 반환된 SecurityReference 객체는 보통 Swagger 설정에서 보안 관련 설정을 적용하고 API 문서에서 해당 보안 스킴과 인증 범위를 표시하는 역할을 합니다
    }

    private ApiKey securityScheme(){
        String targetHeader = "Authorization"; //어떠한 헤더 값을 대입할 것인가: Authorization 헤더
        return new ApiKey("JWT",targetHeader,"header");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("TodoList Api Documentation")
                .description("나의 투두리스트 Api 를 테스트 해 봅시다.")
                .version("0.1")
                .build();
    }


}
