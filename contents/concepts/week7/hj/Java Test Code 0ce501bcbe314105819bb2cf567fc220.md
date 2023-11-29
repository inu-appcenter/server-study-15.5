# Java Test Code

상태: In progress

# 1. 자바에서의 테스트 코드

---

## 1.1 JUnit

- **JUnit**은 자바 언어에서 사용되는 대표적인 **테스트 프레임워크**
- **단위 테스트**를 위한 도구를 제공, 또한 **통합 테스트**도 할 수 있는 기능 제공
- **어노테이션 기반의 테스트 방식을 지원** 즉, JUnit을 사용하면 몇 개의 어노테이션만으로 간편하게 테스트 코드를 작성할 수 있다.
- JUnit을 활용하면 **단정문(assert)**을 통해 테스트 케이스의 **기댓값**이 정상적으로 도출 됐는지 **검토** 가능
- **JUnit5**는 스트링부트 2.2 버전부터 사용 가능

---

## 1.2 JUnit 세부 모듈

**JUnit Platfrom**

- **JUnit Platform**은 **JVM**에서 테스트를 시작하기 위한 뼈대 역할을 합니다.
- 테스트를 발견하고 테스트 계획을 생성하는 **테스트 엔진(TestEngine)**의 인터페이스를 가지고 있습니다.
- **테스트 엔진**은 **테스트를 발견하고 테스트를 수행하며, 그 결과를 보고하는 역할**을 수행합니다.
- 각종 IDE와의 연동을 보조하는 역할을 수행합니다.(IDE 콘솔 출력 등)
- **Platform**에는 **TestEngine API**, **Console Launcher**, **JUnit 4 Base Runner** 등이 포함

---

**JUnit Jupiter**

- **테스트 엔진 API** **구현체**를 포함하고 있으며, JUnit 5에서 제공하는 **Jupiter 기반의 테스트**를 실행하기 위한 테스트 엔진을 가지고 있다.
- 테스트의 실제 구현체는 **별도 모듈의 역할**을 수행하는데, 그 중 하나가 **Jupiter Engine**입니다.
- J**upiter Engine**은 **Jupiter API 를 활용해서 작성한 테스트 코드를 발견하고 실행하는 역할**을 수행합니다.

---

**JUnit Vintage**

- **Junit 3, 4**에 대한 **테스트 엔진 API**를 구현하고 있습니다.
- 기존에 작성된 **JUnit 3, 4 버전의 테스트 코드를 실행 할 때 사용되며 Vintage Engine을 포함**하고 있습니다.

---

## 1.3 spring-boot-starter-test 라이브러리

스프링 부트에서 제공하는 spring-boot-starter-test 라이브러리는 JUnit, Mockito, assertJ 등의 다양한 테스트도구를 제공합니다.   

**대표적인 라이브러리**

- **JUnit5**: 자바 애플리케이션 단위 테스트를 지원
- **Spring Test & Spring Boot Test**: 스프링 부트 애플리케이션에 대한 유틸리티와 통합 테스트를 지원합니다.
- **AssertJ**: 다양한 단정문(assert)을 지원하는 라이브러리입니다.
- **Hamcrest:** Matcher를 지원하는 라이브러리입니다.
- **Mockito**: 자바 Mock 객체를 지원하는 프레임워크입니다.
- **Jsonassert**: JSON용 단정문 라이브러리입니다.
- **JsonPath**: JSON용 XPath를 지원합니다.

---

### Mockito?

- Mockito는 개발자가 동작을 직접 제어할 수 있는 가짜(Mock) 객체를 지원하는 테스트 프레임워크.
- 일반적으로 Spring과 같은 웹 어플리케이션을 개발한다고 하면, 여러 객체들 간의 의존성이 존재한다.
- 이러한 의존성은 단위 테스트를 작성하는 것을 어렵게 하는데, 이를 해결하기 위해 가짜 객체를 주입 시켜주는 Mockito 라이브러리를 활용할 수 있다.
- Mockito를 활용함으로써 가짜 객체에 원하는 결과를 Stub 하여 단위 테스트를 진행할 수 있다.

<p style="border: 1px solid #e0e0e0; background-color: #cccccc; padding: 10px; color: #000000;">
💡Stub = “A stub is a short article in need of expansion”, “스텁은 확장이 필요한 짧은 기사” 라는 뜻으로, 아직 다 완성되지 않은 기사나 제목만 있는 기사처럼 함수 이름만 있거나 내용이 완전치 않은 코드를 뜻함. 테스팅에서 사용되는 테스트 스텁은 테스트를 위하여 작성된 코드로 특정한 목적을 위해 사용됩니다.</p>

## 1.4 JUnit에서 테스트를 지원하는 어노테이션

### Junit의 생명주기 관련 어노테이션

**@Test**

- @Test가 선언된 메서드는 테스트를 수행하는 메서드가 된다.
- JUnit은 각각의 테스트가 서로 영향을 주지 않고 독립적으로 실행됨을 원칙으로 @Test 마다 객체를 생성한다.

---

**@BeforeAll**

- 테스트를 시작하기 전에 호출되는 메서드를 정의

---

**@BeforeEach**

- 각 테스트 메서드가 실행되기 전에 동작하는 메서드 정의

---

**@AfterAll**

- 테스트를 종료하면서 호출되는 메서드 정의.

---

**@AfterEach**

- 각 테스트 메서드가 종료되면서 호출되는 메서드를 정의.

---

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled.png)

---

### 예시 코드

```java
public class TestLifeCycle {

    @BeforeAll
    static void beforeAll(){
        System.out.println("## BeforeAll Anotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll(){
        System.out.println("## AfterAll Annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("## BeforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach(){
        System.out.println("## AfterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    void test1(){
        System.out.println("## test1 시작 ##");
        System.out.println();
    }

    @Test
    @DisplayName("Test Case 2!!!")
    void test2(){
        System.out.println("## test2 시작 ##");
        System.out.println();
    }

    @Test
    @Disabled
    void test3(){
        System.out.println("## test3 시작 ##");
        System.out.println();
    }
}
```

---

## JUnit의 Assert 관련 어노테이션

**@assertNotNull(Object actual)**

- 해당 객체가 null이 아닌지 검사하는메서드.

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%201.png)

---

**@assertEquals(expect, actual)**

- 객체 actual이 expect와 같은 값을 가지는지 확인하는 메서드

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%202.png)

---

**@assertTrue(boolean)**

- 주어진 조건이 참인지 확인하는 메서드

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%203.png)

---

**@assertAll(executable…)**

- 모든 확인 구문들을 테스트하는 메서드
- 같은 여러개의 단언문을 사용 할 때, 앞선 테스트가 실패하면 다음 테스트는 실행하지 않는다. 그러므로 다음 테스트가 실패했는지 알 수가 없다.

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%204.png)

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%205.png)

**assertAll() 메서드 사용**

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%206.png)

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%207.png)

---

**@assertThrow(expectedType, executable)**

- executable에서 expectedType의 예외가 발생했는지 확인하는 메서드.

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%208.png)

---

**@assertTimeout(duration, executable)**

- 특정 시간 안에 실행이 완료되는지 확인한다.
- 두 번쨰 매개변수의 람다문이 종료되야 테스트가 끝난다.

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%209.png)

![Untitled](Java%20Test%20Code%200ce501bcbe314105819bb2cf567fc220/Untitled%2010.png)