package com.example.todolist.Config;

import com.example.todolist.Exception.CustomAccessDeniedHandler;
import com.example.todolist.Exception.CustomAuthenticationEntryPoint;
import com.example.todolist.Security.JwtAuthenticationFilter;
import com.example.todolist.Security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers("/**").hasRole("USER") // role과 관련된 기능이 없지만 시큐리티의 role기능을 연습하기 위해서 추가
                .anyRequest().authenticated()

                .and().exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and().exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                .csrf().disable();

        return http.build();
    }
}
