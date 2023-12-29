package com.example.mytodolist.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

@Slf4j
//GenericFilterBean은 기존 필터에서 가져올 수 없는 스프링의 설정 정보를 가져올 수 있게 확장된 추상 클래스입니다.
//다만 서블릿은 사용자의 요청을 받으면 서블릿을 생성해서 메모리에 저장해두고 동일한 클라이언트의 요청을 받으면 재활용하는 구조여서
//GenericFilterBean을 상속받으면 RequestDispatcher에 의해 다른 서블릿으로 디스패치 되면서 필터가 두번 실행 되는 현상이 발생할 수 있다.
//이 같은 문제를 해결하기 위해 OncePerRequestFilter가 등장하였다, 이 클래스도 GenericFilterBean을 상속받고 있습니다. 다만
// 이 클래스를 상속받아 구현한 필터는 매 요청마다 한번만 실행되게끔 구현됩니다.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider)
    {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //JwtTokenProvider를 통해 servletRequest에서 토큰을 추출하고, 토큰에 대한 유효성을 검사합니다.
        String token = jwtTokenProvider.resolveToken(servletRequest);
        log.info("[doFilterInternal] token 값 추출 완료. token : {}",token);

        log.info("[doFilterInternal] token 값 유효성 체크 시작");
        // 토큰이 유효하다면 Authentication 객체를 생성해서 SecurityContextHolder에 추가하는 작업을 수행합니다.
        if(token !=null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        //doFilter() 메서드를 기준으로 앞에 작성한 코드는 서블릿이 실행되기 전에 실행되고, 뒤에 작성한 코드는 서블릿이 실행된 후에 실행.
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
