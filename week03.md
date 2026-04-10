api

- 소프트웨어 간 데이커를 주고받기 위한 통신 규칙을 정의한 인터페이스

rest

자원을 uri로 표현하고 http 매서드로 자원을 조작하는 http 기반 설계 방식

uri vs url

- uri → 자원을 식별
- url → 자원의 위치

rest 구성

- 자원 → uri로 표현
    - 서버가 관리하는 데이터
    - 각 자원은 uri로 구분
    - 클라이언트는 uri를 통해 자원을 요청
- 행위
    - 자원에 대해 어떤 동작을 할지 표현
    - http 매서드 사용
    - get put patch post delete
- 표현
    - client와 server가 데이터를 주고받는 형태
    - json

rest 특징

- clent-server 구조
    - 클라이언트와 서버 역할 분이
    - 서버 : 데이터 및 비즈니스 로직 담당
    - 클라이언트 : 화면, 사용자 정보 관리
- stateless
    - 서버는 이전 요청 기억 x
    - 매 요청마다 필요한 정보 포함
- uniform interface (일관성)
    - 자원은 uri
    - 행위는 http 매서드
    - 구조가 일관

rest api와 restful api

- rest api : rest 설계 원칙을 따르는 api
- restful api : rest 설계 원칙을 잘 지킨 api

crud

- c → create (생성)
- r → read (조회)
- u → update (수정)
- d → delete (삭제)

http 매서드

- get : 데이터 조회
- post : 데이터 생성
- patch : 데이터 일부 수정
- put : 데이터 전체 수정
- delete : 데이터 삭제

crud와 http 매서드의 관계

- create → post
- read → get
- update → patch, put
- delete → delete

put vs patch

- 멱등성의 차이
    - 같은 요청을 보내도 결과가 동일한 성질 (멱등성 고려 필수)
- put은 항상 멱등성 보장 (put이 더 효율적일 수 있음)
- patch는 기존 값을 인지해야 함

soft delete vs hard delete

- 논리적 삭제 (soft delete)
    - 데이터베이스에서 직접 삭제 x
    - 사용자가 데이터에 접근 x
    - 복구 가능 데이터에 사용
- 물리적 삭제 (hard delete)
    - 데이터베이스에서 완전히 삭제
    - 저장 공간 확보, 조회에 용이

soft delete의 장단점

- 장점
    - 복구 가능
    - 이력 보존
    - 참조 무결성 문제 완화
- 단점
    - db에 데이터가 계속 쌓임
    - 조회시 삭제 여부 조건 필요
    - 관리가 복잡

hard delete의 장단점

- 장점
    - 저장 공간 확보
    - 데이터 관리가 단순
    - 조회가 용이
- 단점
    - 복구 불가능
    - 이력 보존 어려움
    - 참조 관계 문제 가능

restful api 설계 원칙

- 명사 사용
- 복수형 사용
- 계층 구조로 표현
- 마지막 / 사용 x
- 소문자 사용
- 확장자 포함 x

rest가 관계 중심 uri사용 이유

- rest는 자원 중심 설계
- uri는 자원의 식별자
- 확장성과 재사용성에 용이
- uri와 http 매서드 역할 분리 가능 (계층 구조로 표현)

api 명세서

- 사용 이유
    - 명확한 소통
    - 일관성 유지
    - 유지보수에 용이
- 작성 방법
    1. 기본 정보 작성
        1. api 이름
        2. 버전
        3. base url
    2. 인증 방식 명시
        1. basic auth
        2. bearer token
        3. api key
        4. OAuth
    3. endpoint와 http매서드 작성
        1. 엔드포인트 uri
        2. http 매서드
        3. 기능 설명
    4. 각 endpoint에 대한 요청 parameter 설명
        1. PathVariable (경로)
        2. RequestParam (쿼리)
        3. RequestHeader (헤더)
        4. RequestBody (본문)
    5. api가 반환하는 응답 구조를 명확하게 설명
        1. json형식으로 작성
        2. 상태 코드 200→ ok, 201→ created, 404→ not found
        3. 응답 예시 : 실제 응답 데이터 예시
    6. api 사용 중 발생할 수 있는 오류 상황과 대응 방법 명시
        1. 400 - bad request
        2. 401 - unauthorized
        3. 500 - internal server error
        4. 오류 메세지 형식 : json 메세지의 형식과 예시

api 문서화 도구

- swagger
- postman
- gitbook
- spring rest docs

<java 객체 생성 방식>

- 개발자가 직접 new 키워드를 사용하여 객체 생성
- 동일 객체 다수 생성 → 메모리 낭비
- 객체 많음 → 관리 어려움
- 객체 간 의존 관계 직접 설정 → 결합도 증가
- 객체 생성 코드 여러 곳 존재 → 수정 시 모든 코드 변경

spring의 객체 관리

- spring container가 대신 생성 하고 관리
- spring이 관리하는 객체 : bean

bean

- 특징
    - new 키워드 사용 x
    - spring이 객체를 생성 및 생명주기 관리
    - 싱글톤으로 관리
    - 동일 객체 재사용 가능
- 효과
    - 객체를 한 곳에서 중앙 관리
    - 코드 변경 사항 쉽게 반영
    - 유지보수 쉬움

bean 등록 방식

- 수동 등록
    - 개발자가 직접 bean을 등록
    - @configuration + @bean 사용 객체 생성 및 등록
    - 외부 라이브러리 객체도 bean 등록 가능
    - 설정 명확, 코드 양 많음
    - 유지보수 번거로움
- 자동 등록
    - annotation이 붙은 클래스는 spring이 자동으로 bean 등록
    - 컴포넌트 스캔을 통해 자동 등록
    - 외부 라이브러리는 자동 등록 x
    - 코드 간결
    - 유지보수 유리

di(의존성 주입)

- 객체 간 결합도 감소
- 코드 테스트 쉬움
- 유지보수 편리
- spring이 객체 관계를 자동으로 연결
- bean은 spring이 관리하는 객체
- di는 bean을 필요한 곳에 주입해주는 방식

bean 주입 방식

1. 생성자 주입 (권장)
    1. 생성자를 통해 필요한 객체를 전달받는 의존성 주입 방식
    2. final 키워드 사용 가능 (불변성 보장)
    3. 객체 생성 시 의존성 확정
2. 필드 주입
    1. 필드에 @Autowired를 사용하여 의존성 주입
3. setter 주입
    1. setter 매서드를 사용하여 의존 객체 주입 장식
    2. 선택젇 의존성이나 변경 가능성이 있는 경우 사용

annotation

- 코드에 특별한 의미와 기능을 부여하는 기술
    - 코드에 대한 정보와 설정을 제공하는 데이터를 설명하는 추가 정보
    - 코드 간결
    - 반복 코드 제거
    - 기능과 역할을 명확하게 표현
    - spring이 객체를 자동으로 관리하도록 도와줌
    - 코드의 구조와 의도를 명확하게 전달 가능

자주 사용 annotation

- @Component → 자동으로 Bean 등록
- @Configuration → 설정 클래스
- @Bean → 수동으로 Bean 등록
- @Transactional → 트랜잭션 처리
- @Value → 외부 설정 값 주입

Lombok

- 반복되는 코드를 자동으로 생성해주는 라이브러리
- @Getter : 필드 값 조회
- @Setter : 필드 값 수정
- @Builder : 객체 생성 시 값을 하나씩 설정할 수 있는 방식
- @NoArgsConstructor : 파라미터 x 기본 생성자를 자동으로 생성
- @AllArgsConstructor : 모든 필드를 매개변수로 받는 생성자 자동 생성
- @RequiredArgsConstructor : final 필드에 생성자를 자동 생성 필수 의존성이 반드시 초기화되도록 강제

@Transactional

- 여러 작업을 하나로 묶어 모두 성공하서나 모두 실패하도록 처리
- 데이터의 일관성을 유지하기 위해 전체 작업 취소
- 매서드를 하나의 작업 단위로 실행 → commit → 오류 발생 → rollback
- 특징
    - A (원자성) : 트랜잭션 내 모든 작업은 모두 성공 or 실패
    - C (일관성) : 트랜잭션 수행 전루 데이터는 항상 정상적인 상태 유지
    - I (격리성) :   트랜잭션 서로 독립적으로 실행, 다른 트랜잭션 영향 x
    - D (지속성) : 트랜잭션 완료 후 결과 영구적으로 저장

Swagger 활용

1. build.gradle 파일에 의존성  추가
2. global/config/SwaggerConfig.java 클래스 생성
3. spring Bean 등록 , 커스텀, 환경 변수 주입
    1. @Configuration
        1. 해당 클래스를 설정 클래스로 등록
        2. Spring이 이 클래스의 Bean들을 관리
    2. @Value

             i.      외부 설정 파일 값을 코드로 가져옴

   c.  @Bean

    1. 메서드의 반환 객체를 Spring Bean으로 등록

   d.  OpenAPI / Server 설정

    1. 메서드의 반환 객체를 Spring Bean으로 등록
    2. Swagger에서 사용할 서버 정보 설정

api 명세서 : https://natural-sunday-d30.notion.site/api-33dee6665cec80b0a963cae44da1b50e?source=copy_link