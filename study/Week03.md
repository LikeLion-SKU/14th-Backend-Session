🌐 API & Spring 개발 핵심 요약 정리
1. API & REST 기본 개념
   📌 API (Application Programming Interface)
   소프트웨어 간 데이터를 주고받기 위한 통신 규칙을 정의한 인터페이스

📌 REST (Representational State Transfer)
자원을 URI로 표현하고 HTTP 메서드로 자원을 조작하는 HTTP 기반 설계 방식

📌 URI vs URL
URI: 자원을 식별 (Identifier)

URL: 자원의 위치 (Locator)

2. REST의 구성 및 특징
   🏗️ REST의 3요소
   자원 (Resource): URI로 표현하며, 서버가 관리하는 데이터. 클라이언트는 URI를 통해 요청.

행위 (Verb): HTTP 메서드(GET, POST, PUT, PATCH, DELETE)를 통해 동작 표현.

표현 (Representation): Client와 Server가 주고받는 데이터 형태 (주로 JSON).

✨ REST의 주요 특징
Client-Server 구조: 서버(데이터/비즈니스 로직)와 클라이언트(화면/사용자 정보)의 역할 분리.

Stateless (무상태성): 서버는 이전 요청을 기억하지 않으며, 매 요청마다 필요한 정보를 포함해야 함.

Uniform Interface (일관성): 자원은 URI로, 행위는 HTTP 메서드로 처리하는 일관된 구조.

💡 REST API vs RESTful API
REST API: REST 설계 원칙을 따르는 API.

RESTful API: REST 설계 원칙을 아주 충실히 지킨 API.

3. CRUD와 HTTP 메서드
   🔄 CRUD 매핑
   Create (생성): POST

Read (조회): GET

Update (수정): PUT (전체 수정), PATCH (일부 수정)

Delete (삭제): DELETE

⚖️ PUT vs PATCH
멱등성(Idempotency): 같은 요청을 여러 번 보내도 결과가 동일한 성질.

PUT: 항상 멱등성 보장 (자원 전체 교체 시 효율적일 수 있음).

PATCH: 기존 값을 인지하고 수정해야 하므로 상황에 따라 멱등성이 다를 수 있음.

4. 데이터 삭제 전략
   🗑️ Soft Delete (논리적 삭제)
   내용: DB에서 실제 삭제하지 않고 접근만 차단. 복구 가능 데이터에 사용.

장점: 복구 가능, 이력 보존, 참조 무결성 완화.

단점: DB 데이터 누적, 조회 시 조건문 필요, 관리 복잡.

🗑️ Hard Delete (물리적 삭제)
내용: DB에서 데이터를 완전히 삭제.

장점: 저장 공간 확보, 데이터 관리 단순, 조회 용이.

단점: 복구 불가능, 이력 보존 어려움, 참조 관계 문제 가능성.

5. RESTful API 설계 및 명세
   📏 설계 원칙
   명사 및 복수형 사용

계층 구조로 표현

마지막 슬래시(/) 사용 금지

소문자 사용 및 확장자 포함 금지

이유: 자원 중심 설계로 확장성과 재사용성을 높이고 메서드와 역할을 명확히 분리하기 위함.

📝 API 명세서 작성법
기본 정보: API 이름, 버전, Base URL.

인증 방식: Basic Auth, Bearer Token, API Key, OAuth 등.

Endpoint & Method: URI, HTTP 메서드, 기능 설명.

Parameter 설명: PathVariable, RequestParam, RequestHeader, RequestBody.

응답 구조: JSON 형식, 상태 코드(200, 201, 404 등), 응답 예시.

오류 대응: 오류 코드(400, 401, 500) 및 메시지 형식 명시.

6. Spring 객체 관리와 Bean
   ☕ Java 객체 생성 방식의 한계
   직접 new 사용 시: 메모리 낭비, 관리 어려움, 결합도 증가, 수정 시 연쇄 변경 발생.

🌱 Spring의 Bean 관리
Bean: Spring Container가 생성하고 생명주기를 관리하는 객체.

특징: 싱글톤(Singleton) 관리, 객체 재사용, 유지보수 용이.

등록 방식:

수동: @Configuration + @Bean (외부 라이브러리 객체 등록 시 유용).

자동: @Component 스캔을 통해 자동으로 등록 (코드 간결).

7. DI (의존성 주입)
   개념: Spring이 객체 간의 관계를 자동으로 연결해주는 방식.

주입 방식:

생성자 주입 (권장): final 키워드 사용 가능(불변성), 객체 생성 시 의존성 확정.

필드 주입: @Autowired 사용.

Setter 주입: 선택적 의존성이나 변경 가능성이 있을 때 사용.

8. 주요 Annotation & 라이브러리
   ✅ 필수 어노테이션
   @Component: 자동 Bean 등록

@Configuration: 설정 클래스 명시

@Bean: 수동 Bean 등록

@Value: 외부 설정값 주입

@Transactional: 트랜잭션 처리 (ACID 원칙 준수: 원자성, 일관성, 격리성, 지속성)

🌶️ Lombok
@Getter / @Setter: 필드 조회 및 수정

@Builder: 빌더 패턴 객체 생성

@NoArgsConstructor / @AllArgsConstructor: 생성자 자동 생성

@RequiredArgsConstructor: final 필드 대상 생성자 자동 생성 (생성자 주입 시 필수)

9. Swagger 활용 및 문서화
   도구: Swagger, Postman, GitBook, Spring Rest Docs 등.

Swagger 설정 순서:

build.gradle 의존성 추가.

SwaggerConfig.java 클래스 생성.

@Configuration으로 설정 클래스 등록.

@Value로 환경 변수 주입.

OpenAPI 객체를 @Bean으로 등록하여 서버 정보 및 커스텀 설정 완료.

api 명세서 : https://natural-sunday-d30.notion.site/api-33dee6665cec80b0a963cae44da1b50e?source=copy_link