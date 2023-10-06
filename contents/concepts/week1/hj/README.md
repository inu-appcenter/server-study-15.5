# Servlet Container와 Spring Container



## 1. Servlet Container는 무엇인가요?

- ### 1.1 정적 웹페이지, 동적 웹페이지

  **정적 웹페이지**는 고정된 콘텐츠로 구성되어있고, 보통 요청이 들어오면 그대로 제공되는 웹페이지, 이러한 웹페이지는 HTML파일로 구성되어있다.

  **동적 웹페이지**는 사용자의 상태나 상품 목록 추가, 게시판에서 글을 추가 등등,  콘텐츠가 동적으로 생성되고 변경되는 웹페이지이다. 서블릿 프로그램이 이에 해당한다.


- ### 1.2 Servlet이란?


    💡CGI(Common Gateway Interface)란 웹 서버와 외부 프로그램 간의 상호 작용을 허용하는 프로토콜 및 표준 인터페이스를 말한다.
    
    
    기존에 웹 서버는 **정적인 페이지**를 보여주는 용으로 만들었기 때문에 사용자의 요청을 받아 정보를 동적으로 생성하고 이를 클라이언트로 보내주는 것이 불가능 했습니다. 따라서 **서버에서 다른 프로그램을 불러내고 그 프로그램의 처리 결과를 클라이언트로 보내줄 수 있는 인터페이스**가 필요했고 그것이 **CGI**입니다. 
    
    허나 이 CGI에도 문제가 있었는데 요청이 들어 올 때마다 **CGI 프로그램이 프로세스 단위**로 실행이 되어 웹 사용자가 많아짐에 따라 서버 부하가 크게 일어났습니다.
    
    <aside>
    💡 멀티 쓰레드란?
    : 하나의 프로그램이 동시에 여러개의 일을 수행할 수 있도록 해주는 것.
    
    </aside>
    
    **서블릿**은 자바 기반의 CGI 프로그램으로 CGI 규칙에 따라 웹 서버와 데이터를 주고받습니다. **서블릿은** 각 요청에 대해 프로세스를 생성해 처리하는 것이 아니라 **프로세스 1개가 있고 스레드 풀이라는 스레드들이 생성**될 수 있는 공간을 만들어 **멀티스레드**로 처리합니다.  


- ### 1.3 Servelt 컨테이너란?

  **서블릿을 관리**하는 역할을 합니다. 여기서 관리란 서블릿을 실행, 생성, 소멸 등의 관리를 말합니다. 서블릿은 스스로 실행할 수 없으며, 서블릿 컨테이너가 실행해줘야합니다. 서블릿은 Request를 처리하는데, **Request를 처리하기 위해 서블릿을 실행하고 관리하는 것**이 서블릿 컨테이너 입니다. 이렇게 **실행(제어, Control)이 서블릿 컨테이너에게 넘어간 것(역전,Inversion)을 제어의 역전(IoC, Inversion of Control)** 이라고 합니다.


- ### 1.4 아파치 톰캣이란?

  **아파치 톰캣**은 동적인 데이터 처리를 위해 자바 서블릿이 실행 될 수 있는 **컨테이너(서블릿 컨테이너)** 환경을 제공하는 **Web Application Server**입니다.

  **일반적인 웹서버**는 정적인 처리만 가능하지만 **Tomcat**은 **Web Application Server**로 웹 서버와는 다르게DB연결, 다른 응용프로그램과 상호 작용 등 **동적인 기능**들을 사용할 수 있습니다. Tomcat은 **Apache 웹서버 일부 기능 + web container** 조합으로 , 웹 서버의 **정적, 동적 데이터 처리** 기능을 모두 포함 하고 있습니다.  **하나의 톰캣은 하나의 JVM을 가지고 있습니다.**


- ### 1.5 톰캣의 구조

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled.png)

  서블릿은 **Web Application Server** 안에서 구동됩니다.

  처음 **클라이언트**의 요청을 받은 **웹서버(apach)** 는 **WAS Server의 웹 서버**에 전달하고, **WAS Server의 웹 서버**는 해당 요청이 동적 페이지 요청일 시 HTTP 요청을 **Servlet Container**에 전달합니다. **서블릿 컨테이너**는 요청을 처리해 **WAS의 웹 서버**로 전달하고 **WAS Server의 웹 서버**는 HTTP 응답을 **웹서버** 에게 전달, **Web Server**는 받은 HTTP 응답을 **클라이언트**에게 전달합니다.


- ### 1.5 Servlet 컨테이너의 특징
    - **서블릿 생명주기 관리**
        1. **init()**

           : 서블릿을 처음 메모리에 올릴 때 실행되어 , 서블릿을 초기화 하며 처음에 한번만 실행

        2. **service()**

           : 요청/응답(request/response)을 처리하며 요청이 GET인지 POST인지 구분하여 doGet() 또는 doPost() 메서드로 분기

        3. **destroy()**

           : 서블릿 종료 요청이 있을 때 destroy() 메서드가 실행

            <aside>
            💡 doGet()은Http GET요청을 처리하며 주로 데이터 조회, 읽기 작업에 사용
            doPost()는 Http POST요청을 처리하며 주로 데이터 생성, 업데이트, 삭제와 같은 변경 작업에 사용됩니다.

            </aside>

    - **멀티 스레드 지원 및 관리 (요청을 동시처리, 독립처리 하기 위함)**
        1. 서블릿 컨테이너는 요청이 올 때마다 새로운 자바 쓰레드 생성
        2. Http Service() 실행 후 쓰레드 자동 소멸(처리 완료 후 자원 관리)

- ### 1.6 Servelt 요청 처리 단계

  클라이언트에게 요청이 오면 서블릿 컨테이너는 어떻게 요청 URL에 해당하는 서블릿을 찾아 처리해 응답으로 보내주는 것 일까? **web.xml** 설정파일에 그 정보가 기록되어 있습니다.

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%201.png)

  /hello 라는 요청을 보내면 HelloServlet 이라는 서블릿을 찾아 처리한다고 기술되어 있습니다.

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled.png)

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%202.png)

  WAS로 요청이 넘어온 후 요청을 처리하는 처리 단계는 다음과 같습니다.

    1. WAS의 웹서버는 HTTP 요청을 컨테이너에 전달.
    2. 서블릿 컨테이너는 HttpServletRequest/HttpServletResponse 객체를 생성
    3. 서블릿 컨테이너는 web.xml 설정파일을 참고하여 매핑할 서블릿을 찾고 요청 처리에 필요한 서블릿 인스턴스가 컨테이너에 존재하는지 확인.
    4. 존재하지 않다면, 서블릿 인스턴스를 생성하고 해당 서블릿의 init() 메서드를 호출하여 서블릿 인스턴스 초기화.
    5. 서블릿 컨테이너는 스레드를 생성하고 reqest,response 를 인자로 서블릿 인스턴스의 service() 메소드를 호출하여 요청을 처리 후 WAS의 웹서버에게 처리 결과를 전달 한 다음HttpServletRequest/HttpServletResponse 객체를 소멸.
    6. WAS의 웹서버는 Http 응답을 웹서버 에 전달하고 웹서버는 받은 Http응답을 클라이언트에 전달.

    <aside>
    💡 HttpServletRequest/HttpServletResponse 객체는 소멸되는데 서블릿 인스턴스는 소멸되지 않는다. 왜냐하면 서블릿 컨테이너에서 서블릿 객체는 싱글톤으로 관리되기 때문이다. 다음에 같은 경로로 요청이 들어오면 객체를 생성하지 않고 해당 서블릿 인스턴스를 재사용 한다. 하나의 서블릿 인스턴스를 재사용해 여러개의 HTTP요청을 동시에 처리하므로 서블릿 인스턴스는 Thread-Safe 하지 않다.

    </aside>

  이와 같이 **서블릿 컨테이너**는 **서블릿 인스턴스를 싱글톤**으로 관리하는 역할을 수행합니다.

  **서블릿**은 클라이언트로부터 요청이 들어올 때마다 각 요청에 대해 스레드를 생성하여 **멀티스레드**로 처리가 이루어집니다.

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%203.png)

  **서블릿**은 전통적으로 개발자가 주도하던 객체 생성 및 소멸 등의 프로그램 흐름 제어를 반대로 **서블릿 컨테이너**가 주도해 하는데 제어권이 반전됐다고 해서 이를 **제어 역전(IoC,Inversion of Control)**이라 한다.


## 2. Spring Container는 무엇일까요?

- ### 2.1 프론트 컨트롤러 패턴과 Dispatcher Servlet

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%204.png)

  **서블릿**에서는 요청 경로마다 서블릿을 정의해주는 것은 핸들러마다 **공통된 로직을 중복 작성** 한다는 비효율적인 측면이 있다. (예: 한글 인코딩)

  **서블릿**을 개별적으로 다루어 공통된 로직을 여러번 작성하지 말고 **공통된 로직을 하나의 서블릿**만으로 앞단에 두어 모든 클라이언트 요청을 처리하면 공통된 로직을 매번 작성하지 않아도 되서 개발자는 **핵심 로직**에만 집중 할 수 있을 것입니다. 이러한 디자인 패턴을 **프론트 컨트롤러 패턴**이라 합니다.

  **프론트 컨트롤러의 이점**

    1. 컨트롤러를 구현할 때 직접 서블릿을 다루지 않아도 됩니다.
    2. 공통 로직 처리가 가능합니다.

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%205.png)

  스프링은 프론트 컨트롤러 패턴을 따르고 이를 **DispatcherServlet**이 담당합니다. **DispatcherServlet**은 클라이언트의 요청을 **먼저 받아** 필요한 처리를 한 뒤, 개발자가 구현한 요청에 맞는 **핸들러에게 요청을 Dispatch( 특별한 목적으로 보내다)** 하고 해당 핸들러의 실행 결과를 **Response형태**로 만드는 역할을 합니다.


- ### 2.2 스프링 컨테이너란?

  **스프링 빈** 은 스프링 컨테이너에 의해 관리되는 자바 객체입니다.

  **스프링 컨테이너**는 **스프링 빈을 생성, 관리** 하는 컨테이너 입니다. 서블릿 컨테이너처럼 **IoC개념이** 적용되어 **객체를 컨테이너가 관리**합니다. 또한 **DI를** 이용하여 **의존성을 주입**합니다.

    <aside>
    💡 IoC(Inversion Of Control, 제어의 역전)은 객체에 대한 제어권을 개발자가 아닌  스프링 컨테이너에 있는 것 입니다.
    DI(Dependency Injection)은 객체를 직접 생성하는 것이 아닌 외부로부터 객체를 받아서 사용하는 것 입니다.

    </aside>


- ### 2.3 스프링 컨테이너의 종류

  스프링 컨테이너의 종류는 BeanFactory와 이를 상속한 ApplicationContext 2가지 유형이 존재합니다.

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%206.png)

    1. **BeanFactory**

       BeanFactory는 스프링 컨테이너의 최상위 인터페이스 입니다. 스프링 설정파일에 등록된 Bean 객체를 관리하고 조회하는 기본적인 기능만 제공합니다. 컨테이너가 구동될 때 Bean 객체를 생성하는 것이 아니라 클라이언트의 요청에 의해서 Bean객체가 사용되는 시점 에 객체를 생성하는 방식을 이용하고 있습니다. 보통 BeanFactory를 직접 사용하는 경우는 거의 없기에 ApplicationContext를 스프링 컨테이너라고 부릅니다.

    2. **ApplicationContext**

       일반적으로 Spring Container라고 하면 ApplicationContext를 의미합니다.

       BeanFactory를 포함한 여러 인터페이스를 상속받는 인터페이스입니다.

       ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%207.png)

        - MessageSource : 메세지 다국화를 위한 인터페이스
        - EnvironmentCapabel: 개발, 운영 등 환경을 분리해서 처리하고 , 애플리케이션 구동에 필요한 정보들을 관리하기 위한 인터페이스
        - ApplicationEvenPublisher: 이벤트를 발행하고 구독하는 모델을 편리하게 지원하는 서비스
        - ResourceLoader : 파일, 클래스패스, 외부 등 리소르를 읽어오기 위한 인터페이스


- ### 2.4 스프링 컨테이너의 구조 및 동작 과정

  ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%208.png)

    1. 웹 어플리케이션이 실행되면 서블릿 컨테이너(톰캣 등)실행되고 서블릿 컨테이너는 ‘web.xml’ 을 읽어 애플리케이션 구성 정보를 로드합니다..
    2. web.xml에 등록되어 있는 ContextLoaderListener가 생성된다.(ContextLoaderListener는 ServletContextListener 인터페이스를 구현하고 있으며 , ApplicationContext를 생성하는 역할을 수행)
    3. root-context.xml(applicationContext.xml)을 로드합니다.
    4. root-context.xml에 등록되어있는 Spring Container가 구동됩니다.이 떄 Service, DAO, VO 등 객체들이 생성됩니다.
    5. Request가 들어옵니다.
    6. Dispatcher Servlet이 생성됩니다.
       Dispatcher Servlet은 Front Controller 역할을 수행하며 클라이언트 요청을 적절한 핸들러(컨트롤러)로 분배하고 응답을 생성하는 역할을 합니다.
    7. Dispatcher Servlet이 servlet-context.xml을 로드 합니다.
       servlet-context.xml은 서블릿 컨텍스트의 설정 파일로서, 웹 레이어에서 사용되는 빈들과 MVC설정등을 정의합니다. 이 단계에서 두 번째 스프링 애플리케이션 컨텍스트가 생성되고 초기화됩니다..
    8. 두번째 Spring Container 가 구동되며 servlet-context.xml에 정의된 빈들이 생성되고 초기화됩니다. 이 단계에서 주로 웹 컨트롤러, 뷰 리졸버, 인터셉터 등과 같은 웹 관련 빈들이 초기화됩니다.
    9. Dispatcher Servlet은 클라이언트의 요청을 분석하고, 요청 URL에 매핑된 핸들러(컨트롤러)를 찾습니다. 찾아낸 핸들러에게 요청을 위임하고, 핸들러는 비즈니스 로직을 수행하고 모델 데이터를 생성합니다. 이후 DispatcherServlet은 뷰 리졸버를 사용하여 뷰를 결정하고, 뷰를 렌더링 하여 클라이언트에게 응답을 보냅니다.


- ### 2.5 스프링 컨테이너 생성 과정

  스프링 컨테이너는 xml을 기반으로 만들 수 있고, 어노테이션 기반의 자바 설정 클래스로 만들 수 있습니다. 어노테이션 기반의 자바 설정 클래스로 생성하는 과정을 설명하겠습니다.

    1. **스프링 컨테이너 생성**

       ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%209.png)


    <aside>
    💡 보통 스프링 부트에서는 **@SpringBootApplication**  어노테이션이 붙은 클래스를 가지고 있으며, 이 클래스에서 스프링 컨테이너 설정이 자동으로 이루어집니다. **@SpringBootApplication** 어노테이션은 내부적으로 다음과 같은 어노테이션을 포함합니다.
    
    1. **`@Configuration`**: 이 어노테이션은 해당 클래스가 스프링 컨테이너의 설정 클래스임을 나타냅니다. 스프링 컨테이너는 이 클래스를 스캔하여 빈을 생성하고 설정합니다.
    2. **`@ComponentScan`**: 이 어노테이션은 패키지를 지정하여 해당 패키지와 하위 패키지를 스캔하고, **`@Component`**, **`@Service`**, **`@Repository`**, **`@Controller`** 등과 같은 스프링 빈을 찾아서 등록합니다.
    3. **`@EnableAutoConfiguration`**: 이 어노테이션은 스프링 부트의 자동 구성 기능을 활성화합니다. 자동 구성은 애플리케이션을 시작할 때 필요한 설정을 자동으로 로드 하고 구성합니다.
    </aside>
    
    스프링 컨테이너를 생성할 때는 **구성 정보**를 지정해 주어야 합니다. 여기서는 **AppConfig.class**를 구성 정보로 지정하였습니다. 
    
    1. **스프링 빈 등록**
    
    ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%2010.png)
    
    스프링 컨테이너는 파라미터로 넘어온 **설정클래스 정보**를 사용해서 스프링 빈을 등록합니다. 스프링 빈은 **@Bean** 이 붙은 **메서드 명**을 스프링의 빈의 이름으로 사용하거나 이름을 직접 부여 할 수 있습니다. 
    
    빈 이름은 항상 다른 이름을 부여해야합니다. 같은 이름을부여하면 , 다른 빈이 무시되거나, 기존 빈을 덮어버리거나 설정에 따른 오류가 발생합니다.
    
    1. **스프링 빈 의존관계 설정**
    
    ![Untitled](Servlet%20Container%E1%84%8B%E1%85%AA%20Spring%20Container%20734d0de266d746199b51330314a79edb/Untitled%2011.png)
    
    생성된 스프링 빈에 의존 관계를 설정 해 주어야 합니다. 기존 AppConfig에는 MemberService와 OrderService 가 있는데 이 두 클래스를 생성하기 위해서는 각각 discountPolicy, memberRepository를 의존관계를 주입해주어야 합니다. 이런 정보가 AppConfig라는 구성 정보클래스에 담겨 있기에 이를 베이스로 스프링 빈 의존관계를 설정해줍니다. 
    
    스프링 컨테이너는 위와 같이 설정 정보를 참고해 의존관계를 주입(DI)해 줍니다.
