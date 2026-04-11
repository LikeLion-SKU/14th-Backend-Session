## 백엔드 3주차 세션 정리
* * *
> "API 명세서와 CRUD에 대하여."
> -김현수-
### 1. API는 제대로 알고 넘어가자!
- 소프트웨어 간 데이터를 주고받기 위한 통신 규칙을 정의한 인터페이스인데요.
- 자원을 URI로 표현하고 HTTP Method로 자원을 조작하는 HTTP 기반 설계 방식을 의미합니다.
    - 자원(Resource) -> URI로 표현: 서버가 관리하는 데이터, 각 자원은 URI로 구분
    - 행위(HTTP Method) -> 자원에 대해 어떤 동작을 할지 표현, 주요 Method: GET, POST, PUT, PATCH, DELETE
    - 표현 -> 클라이언트와 서버가 데이터를 주고 받는 형태 ex) JSON, XML
* * *
### 2. REST에 대하여
>"REST 설계 원칙을 따르는 API가 바로 저입니다." -REST API-
#### vs RESTful API
- RESTful은 REST의 설계 규칙을 잘 지켜서 설계된 API를 RESTful한 API라고 합니다.
* * *
### CRUD와 HTTP Method
#### CRUD란?
- C(Create) -> 생성
- R(Read) -> 조회
- U(Update) -> 수정
- D(Delete) -> 삭제
#### HTTP Method란?
- POST -> 데이터 생성
- GET -> 데이터 조회
- PATCH -> 데이터 부분 수정
- PUT -> 데이터 전체 수정
- DELETE -> 데이터 삭제
> "아하, 그래서 REST는 URI와 HTTP Method를 통해 CRUD를 수행하는 설계 방식입니다!"
* * *
### 2.1 Create 그리고 POST
> "회원가입, 로그인 요청, 게시글 작성, 댓글 작성"
`@RestController` -> 이 클래스가 REST API를 처리하는 컨트롤러라는 의미
`@RequestMapping` -> 클래스 레벨 공통 경로 설정
`@PostMapping` -> Post 메서드 레벨 세부 경로 설정

### 2.2 Read 그리고 GET
> "사용자 조회, 게시글 조회, 댓글 조회"
`@GetMapping` -> Get 메서드 레벨 세부 경로 설정

### 2.3 Update 그리고 PATCH
> "비밀번호 변경, 닉네임 변경, 게시글 수정, 댓글 수정"
`@PatchMapping` -> Patch 메서드 레벨 세부 경로 수정

### 2.4 Update 그리고 PUT
> "비밀번호 변경, 닉네임 변경, 게시글 수정, 댓글 수정"
`@PutMapping` -> Put 메서드 레벨 세부 경로 수정

#### 여기서 잠깐! PUT vs PATCH, 비슷한 건가요?
> "멱등성의 차이!"
- 멱등성은 같은 요청을 여러 번 보내도 결과가 동일한 성질인데요.
    - PUT은 요청된 Body로 덮어쓸 데이터가 위치해야 하며, 기존의 리소스가 해당 데이터로 완전히 덮어씌워집니다.
    - 따라서, 동일한 요청을 여러번 보내더라도 결과가 동일하므로 멱등성을 가집니다!
    - PATCH는 이와 달리 증가나 추가와 같은 동작도 수행할 수 있는데요. 전체 데이터를 덮어쓰는 것이 아닌, 변경이 필요한 부분만 선택적으로 수정합니다.
    - 따라서, 동일한 요청을 여러 번 보내면 결과가 계속 변경될 수 있으며 멱등성을 보장하지 않습니다!
  
#### HTTP 메소드에서 멱등성이 필요한 이유는?
- 요청 실패 시 재시도 가능 여부 판단 기준
- 멱등하지 않은 경우, 중복 요청으로 문제 발생 가능
- cf. PATCH 요청이 오히려 PUT 요청보다 더 많은 데이터나 복잡한 내용을 포함하게 된다면, 리소스를 전체 교체하는 PUT 방식이 더 적절할 수 있습니다.

### 2.5 Delete 그리고 DELETE
> "회원 탈퇴, 게시글 삭제, 댓글 삭제"
`@DeleteMapping` -> Delete 메서드 레벨 세부 경로 수정

#### Soft Delete vs Hard Delete
- Soft Delete: DB에서 데이터를 직접 삭제하지 않고, 사용자 입장에서는 데이터에 접근할 수 없게 하는 방식.
    - 보통 테이블에서 is_deleted, deleted_at, status 컬럼을 만들어 boolean 값으로 데이터 사용여부를 결정
    - is_deleted = 0 이면 조회 가능, is_deleted = 1 이면 조회 불가능
- Hard Delete: DB에서도 데이터를 완전히 삭제함. 더이상 사용하지 않는 데이터를 DB에 저장하는 것은 저장공간을 낭비하는 것일 수도 있음.

* * *

### 3. REST 설계 원칙
>  "요렇게 설계해줘요잉."
#### 명사를 사용한다
- /create-user (X)
- /posts/1 (O)
#### 복수형을 사용한다
- /post/1 (X)
- /posts/1 (O)
#### 계층 구조를 표현한다
- /users/3/posts (O)
#### 마지막 슬래시(/)를 사용하지 않는다
- /posts/ (X)
- /posts (O)
#### 소문자를 사용한다
- /Users (X)
- /users (O)
#### 확장자를 포함하지 않는다
- /photos.jpg (X)
- /photos (O)

### 3.1 왜 REST는 관계 중심 URI를 사용할까?
#### REST는 자원 중심 설계
- URI는 "무엇을 할까"가 아니라 "어떤 자원인가"를 표현
- /getUserPosts (X) -> 행위 중심
- /users/3/posts (O) -> 자원 중심

#### URI는 자원의 식별자
- URI는 데이터를 찾기 위한 주소
- /users/3/posts -> 해당 위치의 데이터 요청
- /getUserPosts?userId=3 (X)

#### 확장성과 재사용성
- /users/3/posts (O) /getUserPosts (X)
- /users/3/comments (O) /getUserComments (X)
- /users/3/orders (O) /getUserOrders (X)

#### URI와 HTTP Method의 역할 분리
- GET /users/3/posts -> 조회
- POST /users/3/posts -> 생성
-> 하나의 URI로 다양한 동작 처리 가능

* * *
### 4. API 명세서란?
- API의 동작 방식, Endpoint, 요청 및 응답 구조, 인증 방식 등을 정리하여,
- API 사용 방법을 명확하게 설명하는 문서
- QueryString: URL에 추가 정보를 전달하는 파라미터(?key=value 형태)
- ex. /api/v1/projects?page=1&size=10

### 4.1 API 명세서, 그래서 어떻게 작성하나요?
> "필요한 정보들을 명확하게, 꼼꼼하게 작성합시다! 개발자를 위하여..."
#### 기본 정보 작성
- 첫 부분에는 기본 정보를 포함합니다.
    - API 이름
    - 현재 API 버전 (ex. v1, v2)
    - Base URL(API 기본 주소/ex. api.skulikelion.com)
#### 인증 방식 명시
- 필요한 인증 방법을 작성합니다.
    - Basic Auth: 아이디/비밀번호 기반 인증
    - Bearer Token: 토큰 기반 인증
    - API Key: 고유 키로 인증
    - OAuth: 외부 서비스 기반 인증(소셜 로그인)
#### Endpoint와 HTTP Method 작성
- 각 기능에 대한 접근 경로와 요청 방식을 작성합니다.
    - 엔드포인트 URI: /projects, /projects/{project-id}
    - HTTP Method: GET, POST, PUT, PATCH, DELETE
    - 설명: 해당 API의 기능과 목적

ex) GET /api/v1/projects/123?status=active&page=1
- /projects/123: Path Variable -> 특정 자원(프로젝트 ID)를 식별하는 값
- ?status=active&page=1 -> Query Parameter;조회 조건이나 추가 옵션을 전달하는 값

#### 각 Endpoint에 대한 요청 Parameter를 설명
- 경로(PathVariable): /stores/{storeId} -> 특정 자원을 식별할 때 사용 (예:매장 상세 조회)
- 쿼리(RequestParam): ?keyword=value -> 검색, 필터, 페이지네이션 등 추가 조건 전달
- 헤더(RequestHeader): Authorization, Content-Type 등 -> 인증 정보나 요청 설정 전달
- 본문(RequestBody): JSON 형식 데이터

#### API가 반환하는 응답 구조를 명확하게 설명
- 응답 형식: 주로 JSON 형식으로 작성
- 상태 코드:
    - 200 OK
    - 201 Created
    - 404 Not Found 등등...

#### API 사용 중 발생할 수 있는 오류 상황과 대응 방법을 명시
- 상태 코드:
    - 400 Bad Request
    - 401 Unauthorized
    - 500 Internal Server Error
- 오류 메세지 형식: 오류 발생 시 반환되는 JSON 메세지의 형식과 예시 작성

* * *
### 5. Bean에 대하여
>"콩...?"
#### Java의 객체 생성 방식
ex. Calculator calculator = new Calculator();
- 개발자가 직접 new 키워드를 사용하여 객체를 생성합니다!
    - 동일한 객체를 여러 번 생성하고 객체가 많으면 관리하기가 어려우니까요...!
- 따라서, 객체를 개발자가 직접 생성하지 않고 Spring Container가 대신 생성하고 관리합니다.
- Spring이 관리하는 객체를 바로바로 Bean이라고 합니다!!

### 5.1 Bean이란?
>"Spring Container가 생성하고 관리하는 객체이지요!"
- new 키워드를 직접 사용 X
- Spring이 객체를 생성하고 생명주기 관리
- 기본적으로 싱글톤(Singleton)으로 관리
- 동일 객체 재사용 가능

#### 수동 등록
- 설정 클래스에서 직접 Bean 등록
- @Configuration + @Bean 사용
- 외부 라이브러리도 Bean 등록 가능

#### 자동 등록
- 특정 Annotation이 붙은 클래스는 자동으로 Bean에 등록
  - @Component, @Service, @Controller 등
- 외부 라이브러리 자동 등록 불가

#### DI(의존성 주입)
> "필요한 객체를 직접 생성하지 않고 Spring이 대신 주입해줍니다!"
- 객체 간 결합도 감소
- Spring이 객체 관계를 자동으로 연결
-> Bean은 Spring이 관리하는 객체이고, DI는 그 Bean을 필요한 곳에 주입!

### 5.2 Bean 주입 장식
#### 생성자 주입
- 생성자를 통해 객체 주입
- final 키워드 사용 가능 -> 불변성 보장
- 객체 생성 시 의존성 확정
#### 필드 주입
- 필드에 직접 @Autowired 사용
#### Setter 주입
- Setter 메서드를 사용하여 객체 주입
- 선택적 의존성이나 변경 가능성이 있는 경우 사용

***
### 6. Annotation(@)
>"코드에 특별한 의미와 기능을 부여하는 기술"
- ex. @Override -> 메서드 재정의 표시 (오버라이딩 오류 방지)
- Spring이 객체를 자동으로 관리하도록 도와줌
- @Component -> 자동 Bean 등록
- @Configuration -> 설정 클래스
- @Bean -> 수동 Bean 등록
- @Transactional -> 트랜잭션 처리
- @Value -> 외부 설정 값 주입

### 6.1 Lombok Annotation
>"반복되는 코드를 자동으로 생성해주는 라이브러리입니다!"
- @Getter: 필드 값을 조회할 때 사용
- @Setter: 필드 값을 수정할 때 사용
- @Builder: 객체를 생성 시 값을 하나씩 설정할 수 있는 방식
- @NoArgsConstructor: 파라미터가 없는 기본 생성자를 자동으로 생성해주는 기능
- @AllArgsConstructor: 클래스에 선언된 모든 필드를 매개변수로 받는 생성자를 자동으로 생성해주는 기능
- @RequiredArgsConstructor: final 필드에 대한 생성자를 자동 생성/필수 의존성이 반드시 초기화되도록 강제

### 6.2 @Transactional
>"여러 DB 작업을 하나로 묶어 모두 성공하거나 모두 실패하도록 처리하는 기능"
- 하나의 메서드 안에서 여러 DB 작업이 함께 실행될 때
- 하나라도 실패하면 전체 작업을 취소하여 데이터의 일관성을 유지하기 위해 필요!

***

### 7. Swagger 활용
`implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.1`
-> Swagger openapi-ui

#### SwaggerConfig
>"Swagger(OpenAPI)설정을 위한 Spring 설정 클래스
- @Configuration
  - 해당 클래스를 설정 클래스로 등록
  - Spring이 이 클래스의 Bean들을 관리함
  - @Value
    - 외부 설정 파일 값을 코드로 가져옴
  - @Bean
    - 메서드의 반환 객체를 Spring Bean으로 등록
  - OpenAPI / Server 설정
    - Swagger에서 사용할 서버 정보 설정

***
### 3주차 과제 제출
>

