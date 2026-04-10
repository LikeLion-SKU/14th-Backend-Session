노션 API 명세서
https://developing-option-648.notion.site/API-79652e0aa2ec8398877301fb98b06303?source=copy_link

## API ?
- 소프트웨어 간 데이터를 주고받기 위한 통신 규칙을 정의한 인터페이스
- 프론트엔드와 백엔드가 서로 소통하기 위한 규칙

### REST 설계 방식
- API를 설계하는 방식중 하나
- 자원을 URI로 표현하고 HTTP Method로 자원을 조작하는 HTTP 기반 설계 방식

#### REST 구성
- **자원**
  - URI로 표현하는 것
- **행위**
  - 자원에 대해 어떻게 동작할지 표현하는 것
- **표현**
  - 클라이언트와 서버가 데이터를 주고 받는 형태

#### REST 특징
1. **Client - Server 구조**
   - 클라이언트와 서버의 역할을 분리하는 것
2. **Stateless**
   - 무상태 => 서버의 이전 요청을 기억하지 않는 것
3. **Uniform Interface**
   - 자원 -> URI
   - 행위 -> HTTP Method
4. Cacheable
5. Layered System
6. Self - Descriptiveness

## REST API
- REST 설계 원칙을 지키는 API

### REST API 와 RESTful API
- REST API
  - REST 방식으로 만든 APi
- RESTful API
  - REST 원칙을 **잘** 지킨 API

### REST API 작업
- CRUD
  - C
    - 생성
  - R
    - 조회
  - U
    - 수정
  - D
    - 삭제
- HTTP Method
  - 클라이언트가 서버에게 **어떤 동작**을 할 지 요청하는 방식
  - POST
    - 생성
  - GET
    - 조회
  - PATCH
    - 부분 수정
  - PUT
    - 전체 수정
  - DELETE
    - 삭제

### CRUD와 HTTP Method 관계
- C -> POST
- R -> GET
- U -> PATCH & PUT
- D -> DELETE

### PUT 과 PATCH 차이
- PUT
  - Resource를 전체 교체하는 것
  - 멱등성을 보장
- PATCH
  - Resource를 일부 수정하는 것
  - 멱등성을 일부 보장

#### 멱등성 ?
- 같은 요청을 여러 번 보내도 결과가 동일한 성질

### Soft Delete 와 Hard Delete 차이
- Soft Delete
  - 데이터베이스에서 데이터를 직접 삭제하는것이 아닌 사용자가 데이터에 접근하지 못하게 막는것
  - 삭제 후 복구 가능성과 기록 보존이 필요한 데이터에 사용
- Hard Delete
  - 데이터베이스에서 데이터를 완전하게 삭제하는 것
  - 불필요하거나 복구 & 기록 보존이 필요 없는 데이터에 적합

### REST 설계 원칙
1. 명사 사용
   - 동사 (행위) 금지
2. 복수형 사용
3. 계층 간 구조 표현
4. 마지막에 슬래시(/) 사용 금지
5. 소문자 사용
6. 확장자 포함 x

## API 명세서
- API 동작 방식, Endpoint, 요청 및 응답 구조, 인증 방식 등을 정리해 API 사용 방법을 명확하게 설명하는 문서

## Bean
- Spring Container가 생성하고 관리하는 객체
- new 키워드를 사용하지 않음
- 싱글톤으로 관리
  - 객체를 단 하나만 생성해서 공유하는 방식

### Bean 등록 방법
- 수동 등록
  - @Configuration 과 @Bean을 사용해 객체 생성, 등록
- 자동 등록
  - @Component, @Service, @Controller 등을 클래스 위에 붙여서 Spring이 자동으로 Bean으로 등록

## DI (의존성 주입)
- 필요한 객체를 직접 생성하지 않고 Spring이 대신 주입해주는 방식

### Bean 주입 방식
1. 생성자 주입
   - 생성자를 통해 필요한 객체를 전달받는 의존성 주입 방식
2. 필드 주입
   - 필드에 직접 @Autowired를 사용
3. Setter 주입

## Annotation (어노테이션)
- 코드에 특별한 의미나 기능을 부여하는 기술

### Lombok Annotation
- Lombok
  - 반복되는 코드를 자동으로 생성해주는 라이브러리
- 종류
  - @Getter
    - 필드값 조회할 때 사용
  - @Setter
    - 필드값 수정할 때 사용
  - @Builder
    - 객체를 생성 시 값을 하나씩 설정할 수 있는 방식
    - @AllArgsConstructor와 같이 사용
  - @NoArgsConstructor
    - 파라미터가 없는 기본 생성자를 자동으로 생성해주는 기능
  - @AllArgsConstructor
    - 클래스에 선언된 모든 필드를 매개변수로 받는 생성자를 자동 생성
  - @RequiredArgsConstructor
    - final 필드에 대한 생성자를 자동 생성
    - 필수 의존성이 반드시 초기화되도록 강제함

## Transactional
- 여러 DB 작업을 하나로 묶어 모두 성공 or 모두 실패하도록 하는 기능
- 성공 -> commit
- 실패 -> rollback

### Transaction 특성
- A (원자성)
  - 트랜잭션은 모두 원자적으로 처리 되는것
- C (일관성)
  - 트랜잭션 수행 전후에 데이터는 항상 정상적인 상태를 유지
- I (격리성)
  - 트랜잭션은 서로 독립적으로 실행 + 동시에 실행되는 다른 트랜잭션 영향 안받아야 함
- D (지속성)
  - 트랜잭션이 완료되면 그 결과는 영구적으로 저장

