# ORM과 JPA

상태: In progress

# 1. JPA 등장 배경

프로그램을 실행하는 동안 많은 데이터들이 사용되고 만들어집니다. 우리는 이 데이터들이 프로그램이 종료되어도 사라지지 않고 어떤 곳에 저장되는 것이 필요한데 이 개념이 **영속성(Persistence)**입니다. 자바에선 이러한 데이터의 **영속성을 위한 JDBC**를 지원해줍니다.


💡 **영속성(Persistence)란?** 
데이터를 생성한 프로그램이 종료되더라도 사라지지 않는 데이터의 특성을 말합니다.💡


## 1.1 JDBC란?

**JDBC(Java Database Connectivity)**는 자바 프로그래밍 언어를 사용해 데이터베이스에 접근할 수 있도록 하는 자바 API입니다. 이를 통해서 데이터베이스에 접속하고 SQL을 실행하고, 데이터베이스에서 데이터를 가져오거나 삭제하는 등 데이터를 다룰 수 있게 됩니다.

JDBC가 등장하게 된 이유는 **데이터베이스 접근의 표준화**를 위해서 입니다.

데이터베이스에는 Oracle Database, MySQL, PostgreSQL 와 같이 여러 종류의 데이터베이스가 있습니다. 각각 데이터베이스마다 SQL을 전달하거나 결과를 응답 받는 방법이 다 다릅니다. 

JDBC가 존재하기 전에는 이런 데이터베이스마다 존재하는 고유한 API를 직접 사용했었습니다.

따라서 표준이 필요했고 JDBC의 표준 인터페이스 덕분에 개발자는 데이터베이스를 쉽게 변경할 수 있게 되었고 변경에 유연하게 대처할 수 있게 되었습니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled.png)

### 1.2 SQL Mapper란?

**JDBC**를 통해서 자바에서 직접 DB에 접속하여 데이터를 관리하는게 가능해졌습니다. 하지만 반복되는 CRUD의 SQL문을 작성하는데 시간이 많이 소요되서 불편함이 많았습니다. JAVA는 **객체지향적 프로그램**인데 데이터베이스를 작성하는 코드를 보면 **DBMS**와 다를 바가 없었습니다. 그래서 RDB의 **데이터를 객체화 시켜** java처럼 사용하기 위해 **ORM** 과 **SQL Mapper**가 등장하였습니다.

**SQL Mapper**

- SQL 문을 사용하여 DB에 접근하고 쿼리 수행 결과 데이터를 객체의 필드와 맵핑합니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%201.png)

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%202.png)

SQL문을 이용하여 RDB에 접근하고 **데이터를 객체화** 시켜줍니다. sql 쿼리를 그대로 사용하기 때문에 db 튜닝이 수월합니다. 또한 sql 세부적인 내용 변경 시 ORM 보다 편합니다.

하지만 **DBMS에 종속적**이며, **SQL을 개발자가 직접 작성** 해야 하고, 비슷한 **CRUD 코드가 매우 반복**됩니다. 또한 **RDB 마다 sql 문법이 다 다르기 때문에** RDB를 바꾼다면 그에 맞는 sql문법을 다 수정 해야 합니다. 

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%203.png)

### 1.3 ORM 이란?

SQL Mapper는 DBMS의 강한 의존성과 쿼리 직접 작성 등 여러 불편함이 존재하였습니다. 그러한 불편함을 해결하기 위해 **ORM 프레임워크가** 등장했습니다.

**ORM(Object Relational Mapping, 객체- 관계 매핑)** 이란 객체와 관계형 데이터베이스의 데이터를 자동으로 **매핑(연결)**해주는 것을 말합니다.

**SQL Query**가 아닌 직관적인 **코드(메서드)** 로 데이터를 조작합니다. 

예) SELECT * FROM user; → user.findAll();

객체 모델과 관계형 모델 간에 불일치는 **ORM**을 통해 객체 간의 관계를 바탕으로 **SQL문을 자동으로 생성**하여 불일치를 해결합니다.

**-불일치 종류-**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%204.png)

### 1.4 ORM 의 장단점

- 장점
    - SQL문이 아닌 **클래스의 메소드**를 통해 DB를 조작할 수 있으므로 개발자가 객**체 모델만 이용해서 개발**을 하는 데 집중 할 수 있습니다.
    - 재사용 및 유지보수의 편리성이 증가합니다.
    - **DBMS에 대한 종속성**이 줄어듭니다.
        - 개발자는 **Object에 집중함**으로 극단적으로 DBMS를 교체하는 큰 작업에도 적은 리스크와 시간이 소요됩니다.

- 단점
    - ORM으로만 완벽한 서비스를 구현하기 어렵습니다.
    - 프로시저가 많은 시스템에선 ORM의 객체 지향적인 장점을 활용하기 어렵습니다.
    

## 1.6 JPA란?

JPA가 탄생하기 전에는 SQL Mapper를 이용해서 DB쿼리를 작성했는데. 실제로 개발하는 시간보다 SQL을 다루는 시간이 더 많았다고 합니다. 

**객체 지향 프로그래밍**을 하는 것이 아닌 **테이블 모델링**에만 집중하고 있는 불편함을 막기 위해 탄생한 것이 JPA입니다.

**JPA**는 **자바 ORM(Object Relational Mapping)** 기술에 대한 **API 표준 명세**를 뜻합니다.  

**JPA**는 **라이브러리**가 아닌 **ORM**을 사용하기 위한 **인터페이스의 모음**이라고 할 수 있습니다.

아래는 JPA의 핵심이 되는 **EntityManager**입니다. interface로 정의 되어 있는 것을 알 수 있습니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%205.png)

이러한 JPA는 **인터페이스의 모음, 단순한 명세**이기 때문에 **구현이 없습니다.** 

따라서 이러한 **JPA의 구현체**가  ****있어야 **JPA**를 사용할 수 있는데 

그 구현체 중 하나는 **Hibernate** 가 있습니다.


💡 **Hibernate란?** 
: **JPA**를 사용하기 위해서 JPA를 **구현한 ORM 프레임워크** 중 하나입니다. **ORM 프레임워크** 중 가장 많이 사용되며, J**PA인터페이스의 실제 구현부**를 담당합니다.💡



## 1.7 JPA 동작 원리

### 1. **JPA의 핵심**

JPA는 OOP와 RDB의 관계 표현 형식을 서로가 이해할 수 있도록 매핑하는 것이 주요 기능입니다.

즉, OOP 형식으로 짜인 Java 코드를 RDB가 이해할 수 있는 형식으로 번역 되어야 합니다.

그러므로 이를 위해서 다음 2가지를 구현해야합니다.

- 객체를 이용한 RDB형식의 매핑(= 구조적 특징)
- 객체를 이용한 RDB 매핑을 가능하게 하는 내부구조 및 흐름(= 내부 동작 원리)

### 2. Persistence Context

**영속성 콘텍스트란**, JPA의 내부 흐름 또는 동작 원리 그 자체를 의미합니다. 

그러므로 하나의 기술이나 기능이라기 보다는 **논리적인 개념**에 가깝습니다.

흔히 영속성 콘텍스트를 설명할 때 다음과 같이 표현합니다.

→ Entity를 영구적으로 저장하는 것 이다.(영구 저장 == DB 저장)

그러나 **영속성 컨텍스트**는 단순히 **DB에 객체 데이터를 저장**하는 것이 아닌 **Entity를 영속화** 시키는 것 입니다.

### 3. **Entity와 Entity ManagerFactory와 Entity Manager**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%206.png)

- **Entity**

**Entity는** DB에 존재하는 하나의 **Table을 클래스**로 구현한 것 이다. 

쉽게 말해서, **DB Table의 column 과 대응하는 속성**을 가진 클래스를 의미합니다.

- **Entity ManagerFactory**

**JPA**를 시작하려면 설정정보를 사용해서 **Entiy Manager Factory**를 생성 해야 한다. **persistence.xml**의 설정 정보를 읽어서 JPA를 동작 시키기 위한 **기반 객체**를 만들고 **JPA 구현체에** 따라서는 **데이터베이스 커넥션 풀**도 생성하므로 **Entity ManagerFactory**를 생성하는 비용은 아주 큽니다. 따라서 **Entity ManagerFactory는 애플리케이션 전체에서 딱 한 번만 생성**하고 **공유**해서 사용 해야 합니다.

- **Entity Manager**

**Entity Manager**는 위에서 설명한 **엔티티 구현체의 생명주기**를 관리합니다.

**Entity Manager**는 **Entity 객체**를 **영속성 콘텍스트**에 저장하여 관리합니다. 즉 **엔티티와 영속성 콘텍스트 사이에 존재하는** 역할입니다.

### 4. **엔티티 생명주기**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%207.png)

- **비영속(new/transient)**

**영속성 컨텍스트**와 전혀 관계가 없는 새로운 상태 

**Entity가** 생성되고 **Entity Manager**에게 등록되지 않은 상태.  

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%208.png)

- **영속(managed)**

**Entity**가 **영속성 콘텍스트**에 의해 관리되는 상태

즉, **Entity**가 **영속성 콘텍스트**에 등록되어있는 상태를 의미.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%209.png)

→ 영속 상태가 되었다고 바로 DB에 저장되는 것이 아니다.

→ **DB에 저장되는 시점은 persist() 를 commit 한 다음, 해당 Transaction이 온전히 종료됐을 때다.**

- **준영속 (detached)**

**Entity**가 영속성 컨텍스트에 저장되었다가 분리된 상태

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2010.png)

- **삭제(removed)**

DB에 저장되어있는 **Entity**가 삭제된 상태

즉, DB로부터 삭제된 **Entity의 상태를** 의미합니다.

### 5. **Entity Manager의 역할**

위에서 Entity Manager는 Entity와 영속성 컨텍스트 사이에 존재한다고 말했습니다. 그렇다면 왜 Enity Manager는 중간에 위치하는걸까요?

쉽게 말하면, 1차적으로 끝날 과정에 하나의 단계를 추가함으로써 **중간 처리 단계**를 만들 수 있기 때문입니다.

즉, 객체 데이터를 **영속성 컨텍스트**에 바로 저장하면 **데이터에 대한 후처리**를 진행할 수 없습니다.

그리고 **Entity Manager**는 단순히 Entity를 영속성 컨텍스트에 등록/저장하는 것 외 다음과 같은 기능을 합니다.


💡 **Entity Manager**를 통해 사용되는다양한 기능을 알아볼 것이지만, 설명되는 모든 기능들은 모두 **Persistence Context(영속성 컨텍스트) 의 기능**입니다.
**Entity Manager**를 사용하는 것이 결국 **Persistence Context를 사용하는 것** 이므로 오해하면 안됩니다.
💡


- **1차 캐시**

**Entity Manager**는 1차 캐시를 가지고 있습니다.

**Entity Manager**를 통해 Entity를 **PersistentContext**에 등록하면 1차 캐시에 등록이 됩니다.

1차 캐시는 다음과 같이 동작합니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2011.png)

→ Entity Manager을 통해 Entity를 조회하는 경우, DB를 조회하기 전에 **1차 캐시를 우선 조회**합니다.

→ 이처럼 1차 캐시를 우선 조회하는 경우, 응답 속도가 훨씬 빠르다는 장점이 있습니다.

→ **만약 1차 캐시에 존재하지 않는 다른 Entity를 조회**한다면, **1차 캐시를 조회한 후 DB에 접근하여 조회**합니다.

→ 이후 DB에서 반환된 값을 Entity Manager의 1차 캐시에 저장하고 나서 Client에게 반환이됩니다.


💡 **Entity Manager는 하나의 트랜잭션이 시작하면서 생성되고 종료되면서 삭제됩니다.** 
그러므로 **1차 캐시는 하나의 트랜잭션 안에서만 사용되는 캐시**입니다.
애플리케이션 전체가 공유하는 캐시는 **2차 캐시**라고 부릅니다.💡



- **동일성(identity) 보장**

**Entity Manage**r는 소속된 트랜잭션과 **동일한 LifeCycle**을 가집니다.

그러므로 **하나의 트랜잭션 동안 동일한 객체를 여러 번 조회하면 이를 같은 값으로 처리합니다.**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2012.png)

→ 사실 자바의 관점에서 보면 a와 b는 서로 다른 주소 값을 갖는 객체입니다.  하지만 **JPA는 하나의 트랜잭션 안에서 조회되는 동일한 객체에 대해서 같은 객체로 처리**합니다.

→ 즉, **동일성**이 보장됩니다.

→ 이 동일성 또한 **Entity Manager의 1차 캐시** 덕분에 가능합니다.

→ 동일한 SQL을 반복해서 수행하면 **DB로부터 값을 조회하지 않고, 1차 캐시에서 조회**하기 때문에 가능한 것입니다.

- **쓰기 지연(transactional write-behind)**

**commit()**을 수행하기 전까지 **Entity Manager**는 SQL을 작성하지도, DB에게 전달하지도 않습니다.

이 기능을 **쓰기 지연**이라고 부릅니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2013.png)

— 위의 코드가 진행되는 과정을 설명하면 다음과 같습니다.

1. **em.persist(userA)**가 실행됩니다.
2. INSERT 쿼리가 생성되며 Persistence Context **내부의 쓰기 지연 SQL 저장소**에 쌓입니다.
3. **em.persist(userB)**가 실행됩니다.
4. INSERT 쿼리가 생성되며 Persistence Context **내부의 쓰기 지연 SQL 저장소**에 쌓입니다.
5. **transaction.commit()**이 실행됩니다.
6. 쓰기 지연 SQL 저장소에 쌓여있던 **쿼리 명령어**가 **DB에 전달**됩니다. → **flush**
7. DB에서 전달 받은 **쿼리 명령어를 실행하고 결과를 저장**합니다. →commit


💡 **쓰기 지연 기능을 사용하는 이유는?**
→ 그 이유는 DB와의 네트워킹 횟수가 있습니다.
실제로 DB에서 **한 번의 커밋은 하나의 트랜잭션**을 의미합니다. 즉, DB의 값이 변경되는 작업의 처리 단위를 의미하는 것 입니다.
쿼리 하나당 한 번의 커밋을 수행한다는 것은 **10개의 쿼리를 수행하기 위해 10번의 커밋**을 필요로 한다는 것 입니다.
이를 백엔드 관점에서 보면 10번의 쿼리를 수행하기 위해 10번의 DB 통신을 필요로 하는 것 입니다.
즉, **10개의 쿼리를 처리하기 위해 10개의 트랜잭션(=Entity Manager)를 생성하고 10번의 커밋을 수행** 하는 것 입니다.
즉, 이처럼 다수의 쿼리를 묶어서 처리하면 네트워킹 횟수가 현저히 줄어들고 시스템 부하가 감소하게 됩니다.💡



- **변경 감지(Dirty Checking)**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2014.png)

→ 우선 DB에서 userA라는 값을 가진 객체를 조회합니다.

→ 그리고 조회된 객체의 값을 변경하였습니다.

= user1.setName(”Hello”)

→ 값을 변경한 다음 **em.persist(user1)**을 실행해야 되는 것 아닌지 의문이 들 수 있는데 필요 없습니다.

→ JPA의 핵심은 **DB의 데이터를 객체처럼 다루도록** 하는 것 입니다. 즉, 자바 collection에 데이터를 저장하고 수정하는 것 처럼 DB의 데이터를 다루도록 합니다.

→ 예를 들어, 배열에 저장된 값을 변경하고 다시 해당 값을 배열에 저장하지 않는 것처럼 말입니다.

- **JPA 내부적으로는 어떻게 동작할까?**

위의 코드를 실행하면 JPA 내부적으로 **update 쿼리**가 생성됩니다.

JPA가 값이 변경됨을 알 수 있는 이유는 **1차 캐시**를 사용하기 때문입니다.

위에서 DB 데이터를 조회하는 경우, **조회된 데이터를 1차 캐시에 저장한 후 Client에게 반환한다고** 설명했습니다. 

그럼 위의 예시 코드에서 **“userA”를 조회한 시점에서 DB로부터 반환된 객체의 값이 1차 캐시에 저장**됩니다. 

해당 객체가 **DB로부터 가장 최근에 조회된 값을 1차 캐시의 snapshot 속성에 저장**합니다.

transaction.commit() 이 호출되면 **DB에게 쿼리를 전달하기 전에 JPA 내부에서는 flush() 가 호출**됩니다. 

**flush()**가 호출 된 시점에서 **user1의 값과 1차 캐시에 저장된 snapshot을 비교하여 데이터 변경을 감지**하는 것 입니다.

이 비교 과정에서 **데이터의 수정이나 변경이 감지되면 update 쿼리가 생성되고 쓰기 지연 SQL 저장소에 저장**됩니다.

이후 쓰기 지연 SQL 저장소에 쌓인 쿼리를 DB에게 전달합니다.


💡 **snapshot이란?**
데이터베이스에서 가져온 **엔티티(객체)의 원래 상태**를 나타내는 것 입니다. 영속성 컨텍스트에 저장된 엔티티는 데이터베이스로부터 가져온 **원래상태와 현재 상태(변경된 상태)**를 비교할 수 있도록 스냅샷을 가지고 있습니다.
💡



💡 **flush() 란?**
영속성 콘텍스트의 **변경 내용을 DB에 반영**하는 것 입니다.
즉, **영속성 콘텍스트가 가지고 있는 SQL을 DB에게 전달하는 것** 입니다. 

**flush의 역할**
→ 변경 감지(Dirty Checking)
→ 변경된 Entity의 내용을 쓰기 지연 SQL 저장소에 등록
→ 쓰기 지연 SQL 저장소에 등록되어있는 쿼리를 DB에 전달
💡

## 1.8 Spring Data JPA란?

Spring에서는 JPA의 구현체를 직접 다루지 않으며 이 구현체들을 조금 더 쉽게 사용할 수 있게 추상화 시킨 **Spring Data JPA**를 사용합니다.

또한 Spring Data JPA 는 Spring이 JPA를 Spring에서 더 쉽고 간편하게 사용하기 위해 만든 Spring Data의 프로젝트 중 하나 입니다. 

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2015.png)

사실 Hibernate만 쓰는 것 과 Spring Data JPA를 쓰는 것에는 큰 차이는 없지만 Spring Data JPA를 쓰는 것에는 다음과 같은 이유가 있습니다.

1. **Hibernate 외에 다른 구현체로 쉽게 교체하기 위함.**

Spring Data JPA를 쓰고 있다면 Hibernate가 수명을 다 하더라도 새로운 JPA 구현체로 교체할 수 있습니다. 

1. **관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기 위함.**

서버에 트래픽이 많아져 관계형 데이터베이스로는 도저히 감당이 안될 수 있는데, 이때 MongoDB로 교체가 필요하다면 개발자는 **Spring Data JPA**에서 **Spring Data MongoDB**로 의존성만 교체하면 됩니다. 이는 Spring Data의 하위 프로젝트들의 기본적인 CRUD 인터페이스가 같기 때문입니다.

1. **CRUD 쿼리를 직접 작성 할 필요가 없음.**

Spring Data JPA는 Repository 인터페이스를 정의하고, 이를 통해 다양한 데이터베이스 액세스 작업을 간단하게 수행할 수 있도록 정의하고, 이를 통해 다양한 데이터베이스 작업을 간단하게 수행할 수 있도록 지원합니다. 

## 1.9 Repository Layer란?

- **발생 배경**

**비즈니스 로직**은 프로그램의 핵심이 되는 요소이며, 비즈니스 로직을 잘 짜야 원하는 결과를 올바르게 도출 할 수 있습니다. 이 때  비즈니스 로직은 보통 **데이터베이스나 웹서비스** 등의 데이터 저장소에 접근하게 되는데 이 과정에서 여러 문제가 발생할 수 있습니다. 주로 중복되는 코드, 오류를 발생할 가능성이 있는 코드, 오타 등등이 있습니다.

이에 따라 몇 가지 요구사항이 발생하는데.

1. 비즈니스 로직과 데이터 레이어를 **분리**해야 하고
2. 중앙 집중 처리 방식을 통해 **일관된 데이터와 로직**을 제공 해야 합니다.

- **Repository**

따라서 이 문제를 해결할 수 있는 디자인 패턴인 Repository Pattern이 등장하였습니다. Repository는 **데이터 소스 레이어와 비즈니스 레이어** 사이를 중재합니다. Repository는 데이터 소스에 쿼리를 날리거나, 데이터를 다른 domain에서 사용할 수 있도록 새롭게 mapping 할 수 있습니다.

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2016.png)

- **개념**
1. 데이터가 있는 여러 **저장소를 추상화하여** 중앙 집중 처리 방식을 구현할 수 있습니다.
2. 데이터를 사용하는 Domain에서는 **비즈니스 로직**에만 집중 할 수 있습니다.
    
    예를 들어 , ViewModel에서는 데이터가 **로컬 DB**에서 오는지, **서버에서 API응답**을 통해 오는 것인지 출처를 몰라도 됩니다. **Repository를 참조하여 Repository가 제공해주는 데이터**를 이용하기만 하면 됩니다.
    
3. **Repository가 추상화**되어 있으므로 항상 같은 **Interface**로 데이터를 요청할 수 있습니다. ViewModel이 여러 Repository를 공유 하더라도 **일관된 Interface**를 통해 데이터의 일관성 또한 유지할 수 있습니다.


💡 **동일한 인터페이스?**
여러 데이터 엑세스 클래스가 있다 해도 이들 클래스는 모두 **findAll(), findById(), save()** 등과 같은 메서드를 제공하는 **Repository 인터페이스를 구현**하기 때문에 다른 컴포넌트에서 클래스간에 일관된 방식으로 데이터를 요청하고 처리할 수 있습니다.
💡

- **장점**
1. 데이터 로직과 비즈니스 로직을 분리할 수 있습니다.
2. Domain에서는 일관된 인터페이스를 통해 데이터를 요청할 수 있다.
3. 데이터 저장소의 데이터를 **캡슐화**할 수 있다. **객체지향적인 프로그래밍**에 더 적합하다.
4. **단위 테스트**를 통한 검증이 가능하다.
5. 객체 간의 **결합도**가 감소한다.
6. 어플리케이션의 전체적인 디자인이 바뀌더라도 적용할 수 있는 **유연한 아키텍처**이다.

## 2. JpaRepository 인터페이스

스프링 데이터 JPA는 JPA를 더 편리하게 사용할 수 있도록 스프링 프레임워크가 지원하는 프로젝트이다. 데이터 접근 계층을 개발할 때 지루하게 반복되는 CRUD 문제를 해결해준다.

 우선 CRUD를 처리하기 위한 **공통 인터페이스**를 제공한다. 그리고 Repository 개발 시 **인터페이스만 작성하면 실행 시점에 스프링 데이터 JPA가 구현 객체를 동적으로 생성해서 주입**해준다. 즉 **데이터 접근 계층을 개발할 때 구현 클래스 없이 인터페이스만 작성해도 개발을 완료**할 수 있다.

일반적인 **CRUD 메서드(save, find, delete 등) 은 JpaRepository 인터페이스만 상속 받아도 공통으로 제공**합니다. 그리고 아래 코드처럼 **findByUsername(String username) 처럼 관례에 맞게 메소드 이름을 지어주면 자동으로 스프링 데이터 JPA가 메소드 이름을 분석해 적절한 JPQL을 실행합니다.**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2017.png)

### 1. 의존성 추가

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2018.png)

- Spring Boot를 이용하면 위와 같이 의존성을 추가하면 버전을 포함한 기본적인 설정을 모두 자동으로 해줍니다.
- 자동 설정에 의해 **@SpringBootApplication 어노테이션**이 붙은 클래스를 기점으로 **모든 해당 패키지와 하위 패키지를 모두 스캔**하여 Spring Data JPA 관련 로직들을 **모두 빈 등록**합니다.
- 따라서 **Repository 인터페이스**를 구현하면 해당 인터페이스를 구현한 **클래스를 동적으로 생성한** 다음 **스프링 빈으로 등록**합니다. **개발자가 직접 구현 클래스를 만들지 않아도 됩니다.**

### 2. **스프링 데이터 JPA가 구현 클래스 대신 생성**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2019.png)

- org.springframework.repository.Repository 를 구현한 클래스는 스캔 대상입니다.
- 스프링 데이터 JPA는  MemberRepository 인터페이스의 구현 클래스를 자동으로 생성합니다. 이 구현 클래스는 **프록시 패턴**을 사용하여 실제 데이터베이스 조작 로직을 숨기고 런타임 시에 필요한 기능을 추가합니다.
- 런타임시에 스프링은 MemberRepository 인터페이스에 대한 **프록시 객체**를 생성합니다.
- 스프링 컨테이너는 이 **프록시 객체를 빈으로 등록**합니다. 이렇게 함으로서 애플리케이션에서 ‘MemberRepository’ 를 주입받아 사용할 수 있게 됩니다.

💡
💡 **프록시 패턴?**
프록시는 대리인이라는 뜻으로, **무언가를 대신처리**하는 의미이다.  어떤 객체를 사용하고자 할 때, **객체를 직접 참조하는 것이 아닌 해당 객체를 대리하는 객체를 통해 대상 객체에 접근**하는 방식을 사용하면 해당 **객체가 메모리에 존재하지 않아도 기본적인 정보를 참조**하거나 설정할 수 있고, **실제 객체의 기능이 반드시 필요한 시점까지 객체의 생성을 미룰 수 있습니다.**

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2020.png)

💡

****

### 3. 공통 인터페이스 기능

공통 인터페이스 적용

스프링 데이터 JPA 기반 MemberRepository

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2021.png)

- Geneirc
    - T: 엔티티 타입
    - ID : 식별자 타입(PK)
    
    JpaRepository를 상속 받으며 **제네릭에 회원 엔티티와 회원 엔티티의 식별자 타입**을 지정합니다. 이 구성만 갖춰도 JpaRepository가 제공하는 다양한 기능을 사용할 수 있습니다.  
    
    ### 4. **JpaRepository 인터페이스의 계층 구조**
    

![Untitled](ORM%E1%84%80%E1%85%AA%20JPA%2079f6bdabccd14b89b93f04a0dd23d51e/Untitled%2022.png)

Repository, CrudRepository, PagingAndSortingRepository는 **스프링 데이터 프로젝트**가 공통으로 지원하는 인터페이스 입니다. **스프링 데이터 JPA**가 제공하는 JpaRepository는 추가로 JPA에 특화된 기능을 제공합니다.

 

### 5. **JpaRepository 기본메서드와 쿼리 메서드**

JpaRepository 인터페이스를 구현한 클래스를  Spring JPA가 자동으로 구현합니다. 자동으로 구현된 클래스에는 아래와 같은 **기본 메서드**를 포함합니다.

1. **findAll()**
    
    Member 테이블에서 레코드 전체 목록을 조회
    
    List<Member> 객체가 리턴
    
2. **findById(id)**
    
    Member 테이블에서 기본키 필드 값이 id인 레코드를 조회
    
    Optional<Member> 타입의 객체가 리턴
    
    이 객체의 get 메서드를 호출하면 Member 객체가 리턴 
    
    예) Member m = memberRepository.findById(id).get();
    
3. **save(member)**
    
    Member 객체를 Member 테이블에 저장
    
    객체id가 데이터베이스에 존재하는지 유무에 따라서 INSERT와 UPDATE가 나뉩니다.
    
4. **saveAll(memberList)**
    
    Member 객체목록을 Member 테이블에 저장 
    
5. **delete(member)**
    
    Member 객체의 id(기본키) 속성값과 일치하는 레코드를 삭제합니다.
    
6. **delete(memberList)**
    
    Member 객체 목록을 테이블에서 삭제
    
7. **count()**
    
    Member 테이블의 전체 레코드 수를 리턴
    
8. **exists(id)**
    
    Member 테이블에서 id에 해당하는 레코드가 있는지 true/false를 리턴
    
9. **flush()**
    
    지금까지 Member 테이블에 대한 데이터 변경 작업들이 디스크에 모두 기록.
    

**JpaRepository 규칙에 맞는 메서드**

1. **And**
    
    여러 필드를 and로 검색한다.
    
    예) findByEmailAndUserId(String email, String userId)
    
2. **Or**
    
    여러 필드를 or 로 검색한다.
    
    예) findByEmailOrUserId(String email, String userId)
    
3. **Between**
    
    필드의 두 값 사이에 있는 항목을 검색한다.
    
    예) findByCreatedBetween(Date fromDate, Date toDate)
    
4. **LessThan**
    
    작은 항목을 검색합니다.
    
    예) findByAgeLessThan(int age)
    
5. **GreaterThanEqual**
    
    크거나 같은 항목을 검색합니다.
    
    예) findByAgeGreaterThanEqual(int age)
    
6. **Like**
    
    like를 통한 항목을 검색한다(일부 일치하는 항목 검색)
    
    예) findByNameLike(String name)
    
7. **IsNull**
    
    null인 항목을 검색합니다.
    
    예) findByJobIsNull()
    
8. **In**
    
    여러 값중에 하나인 항목을검색합니다.
    
    예) findByJob(String… jobs)
    
9. **OrderBy**
    
    검색 결과를 정렬하여 전달을 합니다.
    
    예) findByEmailOrderByNamesAsc(String email) 
    

**@Query 어노테이션**

메서드 이름을 기반으로 자동으로 쿼리를 생성하는 것은 좋지만, 때로는 이것만으로 충분하지 않을 수 있습니다. **@Query** 어노테이션을 사용하면 **개발자가 직접 쿼리를 작성**하여 복잡한 검색 또는 조인 작업을 수행할 수 있습니다 사용 예시로는 **JPQL**이 있습니다.

예) Todo 엔티티와 User 엔티티가 있고, Todo는 User와 관계를 맺고 있을 때, **Todo와 User 엔티티 간의 조인**을 수행하고 **특정 사용자 이름**에 따라 Todo를 검색하는메서드.

```java
**@Query(”SELECT t FROM Todo t JOIN t.user u WHERE u.username   “username”)
List<Todo> findTodoByUserUsername(String username);**
```

