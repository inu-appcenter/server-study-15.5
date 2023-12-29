# Spring Security란 무엇인가요?

## 인증과 인가란 무엇일까요?

### 인증

- 사용자가 누구인지 확인하는 절차
- 회원가입을 하고 로그인을 하는 과정

### 인가

- 사용자의 요청을 실행할 수 있는 권한 부여를 확인하는 절차
- 모든 회원의 정보 조회 등의 요청에 대해 이 사용자가 실행할 수 있는 권한이 있는지 확인하는 과정

## Spring Security의 구조

![Untitled](Spring%20Security%E1%84%85%E1%85%A1%E1%86%AB%20%E1%84%86%E1%85%AE%E1%84%8B%E1%85%A5%E1%86%BA%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%AD%204f35b38c10fa4f8ea237c8450336570a/Untitled.png)

### AuthenticationFilter

- 사용자의 Request가 들어오면 이에 대한 인증과 인가를 진행해주는 부분
- Dispatcher Servlet으로 가기 전 단계로 이곳에서 인증과 인가에 대한 부분을 처리한 후에 Dispatcher Servlet으로 요청이 이동

## UsernamePasswordAuthenticationToken

- 사용자의 아이디와 비밀번호로 만들어 지는 검증을 위한 토큰 객체

## AuthenticationManger/ProviderManger

- AuthenticationManger은 Spring Security Filter의 인증 방식을 정의하는 인터페이스
- AuthenticationManager의 일반적인 구현체는 ProviderManger
- ProviderManger는 AuthenticationProvider의 목록을 가지고 있으며, 실제 인증 작업은 AuthenticationProvider에 위임

## AuthenticationProvider

- 사용자 기반의 정보(이 형식에선 아이디,비밀번호)로 사용자를 인증하고, 인증 결과를 Authentication의 객체로 반환

## UserDetailsService

- 유저 정보를 가져오는 인터페이스로 loadUserByUsername 메소드를 오버라이드해 사용한다.

## UserDetails

- 사용자의 정보를 담는 인터페이스
- 계정의 권한, 만료, 자격 등의 정보를 제공하며, Spring Security는 이를 사용하여 사용자의 인증 및 권한을 부여한다

## SecurityContextHolder

- Securty가 최종적으로 제공하는 객체로, 사용자에 대한 인증 정보가 Authentication 객체로 저장되어있다

## Spring Security의 작동 흐름

1. 사용자가 Request를 보낸다
2. DispathcerServlet으로 가기 전, AuthenticationFilter에서 해당 요청을 가로챈다
3. 이 가로챈 요청(아이디와 비밀번호)의 정보를 통해 UsernamePasswordAuthenticationToken 미검증의 Authentication토큰을 생성한다
4. 생성된 토큰을 AuthenticationManger로 전달하고, AuthenticationManger의 구현체인 ProviderManager가 인증을 위해 AuthenticationProvider로 토큰을 전달
5. AuthenticationProvider는 UserDetailsService로 토큰의 정보를 전달해 데이터베이스에서 일치하는 사용자를 찾아 UserDetails 객체를 생성
6. 생성된 UserDetails 객체를 AuthenticationProvider로 전달해 인증을 수행하고 성공 시 검증된 객체인 Authentication을 반환해 ProviderManger를 통해 AuthenticationFilter로 전달
7. AuthenticationFilter는 검증 완료된 해당 토큰을 SecurityContextHolder에 있는 SecurityContext에 저장

## Spring Security에서 할 수 있는 보안 설정

- 기본적인 로그인 구성과 사용자 정의 로그인 처리가 가능한 인증 기능
- 사용자의 권한에 따른 특정 리소스에 접근에 대한 인가 기능
- CSRF 방어 기능
- 세션 관리 기능
- SSL/TLS 설정 기능
- OAuth2, JWT 등의 다양한 인증 방식 지원

## SecurityConfiguration 클래스 설명

SecurityFilterChain을 Bean으로 등록하는 형태로 세부적인 보안 설정이 가능한 HttpSecurity를 이용해 인증, 인가의 설정이 가능한 사용자 정의 보안 설정 클래스이다.

## formLogin(),logut()

- 로그인에 대한 페이지, 로그인 실패 후 이동 페이지, 로그인의 성공, 실패에 대한 핸들러 등 로그인/아웃에 관한 설정들을 설정

## csrf()

- 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위를 특정 웹사이트에 요청하게 하는 공격
- 서버에서 발급 했던 토큰과 클라이언트가 요청을 보낼 때 들어있는 토큰을 비교하여 막는 방식으로 위와 관련된 설정들 가능

## sessionManagement()

- 사용자가 인증을 받으면 세션이 생성되는데, 다른 브라우저나 pc에서 로그인 할 시 기존 세션이 아닌 새로운 세션이 생성되는 이러한 문제점 등을 해결을 위한 세션 관리 설정 가능

## addFilterBefore()

- 지정한 필터 앞에 커스텀 필터를 추가하여 지정한 필터보다 먼저 실행하게 만드는 기능

## authorizeHttpRequests()

- 사용자의 권한에 따른 인가의 대한 설정이 가능하다. 해당 HttpMethod의 방식 별로의 인가에 대한 설정도 가능하다.

## exceptionHandling().~

- 인증, 인가 관련한 예외 처리를 커스텀 예외처리로 가능하게 하는 설정