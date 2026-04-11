## API 명세서 과제 링크
https://burnt-dog-1af.notion.site/3-33f98c4165298079ad17d764d939f8e8?source=copy_link

## 1. API란?
- 소프트웨어 간 데이터를 주고받기 위한 통신 규칙을 정의한 인터페이스

## 2. REST & RESTful API

### 1) REST(Representational State Transfer)
- 자원을 URI로 표현하고 HTTP Method로 자원을 조작하는 HTTP 기반 설계 방식


### 2) REST의 구성
- 자원(Resource)/행위((Method)/표현(Representation)으로 구성
1. 자원(Resource) -> URI로 표현
   - 서버가 관리하는 데이터(사용자, 게시글, 주문 등)
   - 각 자원은 URI로 구분
   - Client는 URI를 통해 자원을 요청
2. 행위 (HTTP Method)
   - 자원에 대해 어떤 동작을 할지 표현
   - HTTP Method 사용
   - 주요 Method: GET|POST|PUT|PATCH|DELETE
3. 표현
   - Client와 Server가 데이터를 주고 받는 형태
   - ex) JSON, XML

### 3) REST 특징
1. Client - Server 구조
   - 클라이언트와 서버 역할 분리
   - 서버 -> 데이터 및 비즈니스 로직 담당
   - 클라이언트 -> 화면, 사용자 정보 관리
2. Stateless (무상태) 
    - 서버는 이전 요청을 기억하지 않음
    - 매 요청마다 필요한 정보 포함
3. Uniform Interface (인터페이스 일관성)
    - 자원은 URI
    - 행위는 HTTP Method
    - 구조가 일관됨

### 4) REST API
- REST 설계 원칙을 따르는 API

### 5) REST API와 RESTful API는 무슨 차이가 있는가?
1. REST API
   - REST 방식으로 만든 API
2. RESTful API
   - REST 원칙을 잘 지킨 API

## 3. CRUD

### 1) CRUD와 HTTP Method
1. CRUD
   - 데이터 관리 및 조작에서 수행되는 4가지 기본 작업
     - C -> 생성
     - R -> 조회
     - U -> 수정
     - D -> 삭제

2. HTTP Method
   - POST -> 데이터 생성
   - GET -> 데이터 조회
   - PATCH -> 데이터 부분 수정
   - PUT -> 데이터 전체 수정
   - DELETE -> 데이터 삭제

### 2) REST와 CRUD, HTTP Method의 관계
- REST는 URI로 자원을 표현하고, HTTP Method로 자원의 동작을 정의한다.
- 이때 HTTP Method는 CRUD와 대응된다.
    -> REST는 URI와 HTTP Method를 통해 CRUD를 수행하는 설계 방식이다.

### 3) PUT vs PATCH
- 멱등성의 차이 : 같은 요청을 여러 번 보내도 결과가 동일한 성질
- PUT은 항상 멱등성을 보장하지만, PATCH는 요청 방식에 따라 멱등성이 보장되지 않을 수 있다.
- [HTTP 메소드에서 멱등성이 필요한 이유]
  - 요청 실패 시 재시도 가능 여부 판단 기준
  - 멱등하지 않은 경우, 중복 요청으로 문제 발생 기능

### 4) Soft Delete vs Hard Delete
- 논리적 삭제 vs 물리적 삭제

1. Soft Delete
   - 데이터베이스에서 데이터를 직접 삭제하지 않고, 사용자 입장에서는 데이터에 접근할 수 없게 하는 방식
   - 보통 테이블에 is_deleted, deleted_at, status 컬럼을 만들어서 boolean 값으로 데이터 사용여부를 결정한다.

   (1) 장점
    - 복구 가능
    - 이력 보존
    - 참조 무결성 문제 완화

   (2) 단점
   - DB에 데이터가 계속 쌓임
   - 조회 시 삭제 여부 조건 필요
   - 관리가 복잡해질 수 있음

2. Hard Delete
   - 데이터베이스에서도 데이터를 완전히 삭제하는 방식을 의미.
   - 더이상 사용하지 않는 데이터를 DB에 저장하는 것은 저장공간을 낭비하는 것일 수 있으므로, 직접 데이터를 삭제함으로써 저장 공간을 확보

   (1) 장점
    - 저장 공간 확보
    - 데이터 관리가 단순함
    - 조회가 용이함

   (2) 단점
   - 복구 불가능
   - 이력 보존 어려움
   - 참조 관계 문제 가능


## 4. REST 설계 원칙

### 1) RESTful URI 설계 원칙
1. 명사를 사용한다
2. 복수형을 사용한다
3. 계층 구조를 표현한다
4. 마지막 슬래시를 사용하지 않는다
5. 소문자를 사용한다
6. 확장자를 포함하지 않는다.

### 2) 왜 REST는 관계 중심 URI를 사용할까?
1. REST는 자원 중심 설계
2. URI는 자원의 식별자
3. 확장성과 재사용성
4. URI와 HTTP Method 역할 분리


## 5. API 명세서란?
- API의 동작 방식, Endpoint, 요청 및 응답 구조, 인증 방식 등을 정리하여 API 사용 방법을 명확하게 설명하는 문서


## 6. Spring Bean
- Spring Boot는 객체를 개발자가 직접 생성하지 않고, Spring Container가 대신 생성하고 관리한다.
- 이때 Spring이 관리하는 객체를 Bean이라고 한다.

### 1) Bean이란?
- Spring Container가 생성하고 관리하는 객체
  - 특징
    - new 키워드를 직접 사용하지 않음
    - spring이 객체를 생성하고 생명주기를 관리
    - 기본적으로 싱글톤으로 관리
    - 동일 객체를 재사용 가능
    - 객체를 한 곳에서 중앙 관리
    - 코드의 변경 사항을 쉽게 반영 가능
    - 유지보수가 쉬워짐

### 2) DI란?
- 필요한 객체를 직접 생성하지 않고 Spring이 대신 주입해주는 방식





