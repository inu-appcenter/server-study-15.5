# Appcenter server study 15.5기
> Since 2023.09.19

## 👨‍💻 운영진

<p>
   <a href="https://github.com/NARUBROWN">
      <img src="https://avatars.githubusercontent.com/u/38902021?v=4" width="100">
    </a>
    <a href="https://github.com/Juser0">
      <img src="https://avatars.githubusercontent.com/u/108407945?v=4" width="100">
    </a>
    <a href="https://github.com/rnwnsgud">
      <img src="https://avatars.githubusercontent.com/u/78197563?v=4" width="100">
    </a>
    <a href="https://github.com/wellbeing-dough">
      <img src="https://avatars.githubusercontent.com/u/102784323?v=4" width="100">
    </a>
</p>

## 👨‍💻  15.5기 스터디원

<p>
	<a href="https://github.com/jang-namu">
      <img src="https://avatars.githubusercontent.com/u/121238128?v=4" width="100">
    </a>
  <a href="https://github.com/squidjiny">
      <img src="https://avatars.githubusercontent.com/u/92552047?v=4" width="100">
    </a>
  <a href="https://github.com/parseyong">
      <img src="https://avatars.githubusercontent.com/u/101376070?v=4" width="100">
    </a>
  <a href="https://github.com/sukangpunch">
      <img src="https://avatars.githubusercontent.com/u/115551339?v=4" width="100">
    </a>
  <a href="https://github.com/hen715">
      <img src="https://avatars.githubusercontent.com/u/130031561?v=4" width="100">
    </a>
</p>

---

## 📘 스터디 진행 내용
- ### [개념 정리](contents/concepts/index.md)
- ### TodoList API
	- [장철희](contents/todoListAPI/ch/README.md)
	- [김정아](contents/todoListAPI/ja/README.md)
	- [박세용](contents/todoListAPI/sh/README.md)
	- [강형준](contents/todoListAPI/hj/README.md)
	- [이혜성](contents/todoListAPI/hs/README.md)
    - [조하은](contents/todoListAPI/he/README.md)
    - [정동현](contents/todoListAPI/dh/README.md)

---

## 📝 규칙

- `커밋 컨벤션`

    - Feat: 새로운 기능 추가
    - Fix: 버그 수정
    - Docs: 문서 수정
    - Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - Refactor: 코드 리팩토링
    - Test: 테스트 코드, 리팩토링 테스트 코드 추가
    - Chore: 빌드 업무 수정, 패키지 매니저 수정
<br><br>
      
- `issue 규칙`
    - 참고: [https://velog.io/@junh0328/협업을-위한-깃허브-이슈-작성하기](https://velog.io/@junh0328/%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B9%83%ED%97%88%EB%B8%8C-%EC%9D%B4%EC%8A%88-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0)
    - 레이블 참고:
      [https://github.com/modolee/github-initial-settings](https://github.com/modolee/github-initial-settings)
    - 제목 참고: [https://doublesprogramming.tistory.com/256](https://doublesprogramming.tistory.com/256)
      <br><br>
    - 템플릿
        - issue 제목
            - 예시: **[Feat] 이슈 정리**
        - issue 템플릿

            ```markdown
            ## 📋 이슈 내용
            
            ## ✅ 체크리스트
            
            ## 📚 레퍼런스
            
            ```
        - 제목 예시
            - [Add] UI button 구현
    <br><br>
- `branch 규칙`
    - 각자의 영어 이름을 딴 branch 명을 사용한다.
    - 예시: 
    ```
  git checkout -b <브랜치명>      
  git checkout -b wonjeong
    ```
    
- `commit message 규칙`
    - 참고: [https://doublesprogramming.tistory.com/256](https://doublesprogramming.tistory.com/256)
    - [종류] 메시지 - #이슈번호
    - 예시
        - [Feat] todo-list 회원 API 엔티티 구현 - #2
        - [Fix] todo-list 회원 단건 조회 서비스 에러 수정 - #2
    <br><br>
- `PR 규칙`
    - PR 템플릿

        ```markdown
        ## 📋 이슈 번호
        
        ## 🛠 구현 사항
        
        ## 📚 기타
        
        ```
---

## 📚 스터디 주제

### Server Concept
- ##### 1주차 ~ 2023-10-05 (목)

- 발표 내용
1. 서버는 무엇이고 어떻게 동작할까요?, 자바 웹 프레임워크는 어떻게 변화해 왔을까요?
    1. 서버와 클라이언트는 무엇일까요?
    2. 자바 웹 프레임워크의 역사
        1. J2EE, EJB, Servlet, JSP는 무엇일까요?
        2. Spring Framework는 어떻게 탄생했나요?
        
2. Spring은 무엇일까요? 또, Spring과 SpringBoot의 주요한 차이점은 무엇일까요?
    1. Spring은 무엇일까요?
    2. Spring은 어떤 구성요소를 포함하고 있나요?
    3. MVC 패턴은 어떤 패턴을 이야기 할까요? 또, 이 패턴은 어떻게 동작하나요?
    4. Spring과 Springboot의 주요한 차이점은 무엇인가요?
    
3. Spring Framework의 주요 특징은 무엇인가요?
    1. Spring Framework의 주요 특징은 무엇인가요?
         1. IoC는 무엇이고 스프링에서 어떻게 활용되나요?
         2. DI는 무엇이고 IoC와 DI는 무슨 관계일까요?
         3. AOP는 무엇이고 어떤 기능이 AOP를 사용하나요?
        
4. Servlet Container와 Spring Container는 무엇인가요? 그리고 어떻게 동작하나요?
    1. Servlet Container는 무엇인가요?
        1. Servlet Container는 사용자의 요청은 어떻게 처리하나요?
    2. Spring Container는 무엇인가요?
        1. 프론트 컨트롤러 패턴은 무엇이고, DispatcherServlet은 무엇일까요?
        2. Spring Container는 Bean을 어떻게 관리하나요?
