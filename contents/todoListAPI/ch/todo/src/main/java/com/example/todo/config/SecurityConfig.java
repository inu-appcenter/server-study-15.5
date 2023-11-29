package com.example.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    csrfFilter : HTTP 요청에 대해 CSRF 토큰을 생성, 키에 저장한다.
    모든 POST, PUT, DELETE 요청에 대해 CSRF 토큰이 함께 전송됐는지 검증.
    CSRF 토큰이 없는 요청은 차단한다.
    ref. https://whitewise95.tistory.com/276
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http.authorizeRequests()
                .anyRequest()
                .permitAll();
        http.formLogin();
        */

        /*
        주의! csrf 비활성화.
        REST API 엔드포인트에 있어서 csrf가 필요하냐는 논쟁거리인듯..
        ref. https://security.stackexchange.com/questions/166724/should-i-use-csrf-protection-on-rest-api-endpoints
         */
        http.csrf().disable();

        return http.build();
    }
}
