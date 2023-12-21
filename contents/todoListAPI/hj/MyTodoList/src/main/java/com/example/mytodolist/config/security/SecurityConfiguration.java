package com.example.mytodolist.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final  JwtTokenProvider jwtTokenProvider;

    //@Autowired 어노테이션이 없으면 Spring은 어떻게 의존성을 제공해야 하는지 모르게 되어 런타임 오류나 예상치 못한 동작이 발생할 수 있습니다.
    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화합니다.
        httpSecurity.httpBasic().disable()
                //Rest Api에서는 CSRF 보안이 필요 없기 때문에 비활성화 하는 로직입니다. CSRF는 Cross-Site Request Forgery의 줄임말로 '사이트 간 요청 위조' 를 의미합니다.
                .csrf().disable()
                //Rest API 기반 애플리케이션의 동작 방식을 설정합니다. 지금 진행 중인 프로젝트에서는 JWT 토큰으로 인증을 처리하며, 세션은 사용하지 않기 때문에 STATLESS로 설정합니다.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //애플리케이션이 들어오는 요청에 대한 사용 권한을 체크합니다. 이어서 사용한 antMatcher() 메서드는 antPattern을 통해 권한을 설정하는 역할을 합니다.
                .authorizeRequests()
                //해당 경로는 모두 허용
                .antMatchers("/users/sign-in","/users/sign-up").permitAll()
                // 'exception' 단어가 들어간 경로는 모두 허용
                .antMatchers("**exception**").permitAll()
                //Swagger 접근 모두 허용.
                .antMatchers("/swagger-resources/**","/swagger-ui/index.html",
                        "/webjars/**","/swagger/**","/users/exception","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                //기타 요청은 'ADMIN' 에게 모두 허용
                .anyRequest().hasRole("ADMIN")
                .and()
                //권한을 확인하는 과정에서 통과하지 못하는 예외가 발생할 경우 예외 전달
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                //인증 과정에서 예외가 발생할 경우 예외를 전달.
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                //addFilterBefore() 메서드를 통해 어느 필터 앞에 추가할 것인지 설정할 수 있는데, 현재 구현돼 있는 설정은,
                //스프링 시큐리티에서 인증을 처리하는 필터인 UsernamePasswordAuthenticationFilter 앞에 앞에서 생성한 JwtAuthenticaionFilter를 추가하겠다는 의미이다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    //WebSecurity는 HttpSecurity 앞단에 적용되며, 전체적으로 스프링 시큐리티의 영향권 밖에 있습니다. 즉, 인증과 인가가 모두 적용되기 전에 동작하는 설정입니다.
    //그렇기에 인증과 인가가 적용되지 않는 리소스 접근에 대해서만 사용합니다.
    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers("/v3/api-docs","/swagger-resources/**","/swagger-ui/index.html","/webjars/**","/swagger/**","/sign-api/exception");

    }
}
