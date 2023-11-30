# Controller Layer Test

## Controller

- 클라이언트의 요청을 받아 요청에 맞는 서비스에 전달해 반환 된 결과를 가공해서 클라이언트에게 응답하는 역할을 수행
- 즉, HTTP 요청과 응답에 대한 기능을 잘 수행하는지 테스트

## @WebMvcTest

- 특정 컨트롤러를 대상으로 테스트를 할 때 사용
- @SpringBootTest 사용 시 모든 빈들을 스캔하지만, @WebMvcTest 어노테이션을 사용하면 컨트롤러와 관련된 빈들만 스캔함으로 비용적으로 효율적임
- @Controller, @RestController, @ControllerAdvice, @RestControllerAdvice, @JsonComponent, Filter, WebMvcConfigurerHandler, MethodArgumentResolver

![Untitled](Controller%20Layer%20Test%2070c7f7c4038e4b8f9124dbf27687ccba/Untitled.png)

```java
@WebMvcTest(MemberController.class)
```

## @MockBean

- @WebMvcTest 를 사용 시 Controller와 관련된 빈들만 스캔하기 때문에 다른 계층의 빈을 스캔 하지 않음, 또한 단위테스트 혹은 슬라이스 테스트를 위해 다른 계층의 의존성을 주입 받지 않기 위해 사용
- 어노테이션 적용 시 가짜 객체를 만들어 컨테이너에 주입
- 가짜임으로 실제 행위를 실행하지 않고, 주입 것에 대한 행위에 대한 결과를 임의로 지정 가능

```java
@MockBean
MemberService memberService;

@EnableJpaAuditing
@SpringBootApplication
public class ToDoListApplication{
//위와 같은 형태로 사용시 단위 테스트에

@MockBean(JpaMetamodelMappingContext.class)
public class MemberControllerTest {
//사용 가능
```

## @SpyBean

- @MockMvc 와는 정반대로 행위에 대한 결과를 임의로 지정한 것 외의 모든 것들이 실제 객체의 것을 사용
- 임의로 결과 값을 설정해주는 것 외에는 @Autowired 와 같이 실제 객체의 메소드를 호출함과 같음으로 성능이 @MockBean보다 낮을 수 있음

## @MockMvc

- @WebMvcTest 내부에 @AutoConfigureMockMvc가 존재함으로 @WebMvcTest 을 사용하면 @Autowired로 주입 받아 사용 가능
- 서버를 실행시키지 않고 테스트용 MVC 환경을 만들어 Request, Reponse 기능을 제공
- MockMvc의 객체의 perform 메소드를 사용해 get, put, post, delete 요청과 contentType, 파라미터, 바디 등 여러 설정 가능
- Object Mapper, gson 라이브러리 등을 이용하여 객체를 json 형식으로 변환 후 request body에 탑재 가능
- perform 메소드의 return 값으로 ResultActions 객체를 받으며, 이 객체는 리턴에 담긴 값을 검증할 수 있는 andExpect 메소드를 제공
- andExpect 메소드를 사용 시 상태 코드, 응답 본문 내용에 대한 검증 ( 값 혹은 존재의 유무) 등의 검증이 가능하고. andDo 메소드의 print()를 호출 시 모든 내용들이 출력

```java
@Autowired
private MockMvc mockMvc;

mockMvc.perform(
                get("/api/members/"+memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.nickname").value("test"))
                .andDo(print());
```