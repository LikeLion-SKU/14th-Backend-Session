# 📌 Week 03 

API 명세서
https://www.notion.so/33c8c3eee59480abb63dd6f36ef02e6d?v=33c8c3eee59480c1964a000cf15079b5&source=copy_link

## 📖 1. API란?
API(Application Programming Interface)는  
클라이언트와 서버 간 데이터를 주고받기 위한 통신 규칙이다.  

- 클라이언트 → 요청(Request)
- 서버 → 응답(Response)
- API → 요청과 응답을 연결하는 인터페이스

---

## 📖 2. REST란?
REST(Representational State Transfer)는  
자원을 URI로 표현하고 HTTP Method로 자원을 조작하는 설계 방식이다. 

### ✔️ 구성 요소
- Resource (자원) → URI
- Method (행위) → HTTP Method
- Representation (표현) → JSON, XML 등

---

## 📖 3. REST 특징
- Client - Server 구조 (역할 분리)
- Stateless (무상태)
- Uniform Interface (일관성)

---

## 📖 4. REST API vs RESTful API
- REST API: REST 방식으로 만든 API
- RESTful API: REST 원칙을 잘 지킨 API 

---

## 📖 5. CRUD와 HTTP Method

### CRUD
- Create → 생성
- Read → 조회
- Update → 수정
- Delete → 삭제

### HTTP Method
- POST → 생성
- GET → 조회
- PATCH → 부분 수정
- PUT → 전체 수정
- DELETE → 삭제 

👉 REST는 URI + HTTP Method로 CRUD를 수행한다.

---

## 📖 6. PUT vs PATCH

### PUT
- 전체 데이터 교체
- 멱등성 보장 (여러 번 요청해도 결과 동일)

### PATCH
- 일부 데이터 수정
- 멱등성 보장 안 될 수 있음 

---

## 📖 7. DELETE 방식

### Hard Delete
- 데이터 완전히 삭제
- 복구 불가능

### Soft Delete
- 삭제 여부만 표시 (is_deleted)
- 복구 가능  

---

## 📖 8. REST 설계 원칙

- URI는 명사 사용 (/users, /posts)
- 소문자 사용
- 복수형 사용 (/posts)
- 계층 구조 표현 (/users/1/posts)
- 동사 사용 금지 (/getPosts ❌) 

---

## 📖 9. API 명세서란?

API의 요청, 응답, Endpoint, Method 등을 정의한 문서이다. 

### ✔️ 필요성
- 개발자 간 소통
- 일관성 유지
- 유지보수 용이

---

## 📖 10. API 명세서 구성 요소

- Endpoint (URI)
- HTTP Method
- Request (요청)
- Response (응답)
- Parameter (Path, Query, Body)
- 설명 (Description)

---

## 📖 11. Parameter 종류

- Path Variable  
  → 특정 자원 식별 (/posts/{id})

- Query Parameter  
  → 조건 전달 (?page=1&size=10)

- Request Body  
  → JSON 데이터 전달

- Header  
  → 인증 정보 전달 (Authorization)

---

## 📖 12. 응답 구조

- JSON 형식 사용
- 상태 코드 포함
    - 200 OK
    - 201 Created
    - 404 Not Found
    - 500 Internal Server Error  

---

## 📖 13. Swagger

Swagger는 API 문서화 및 테스트 도구이다.  

### ✔️ 특징
- 코드 기반 자동 문서 생성
- API 테스트 가능
- 변경사항 자동 반영

---

## 📖 14. Bean이란?

Bean은 Spring Container가 생성하고 관리하는 객체이다.

### ✔️ 특징
- 객체를 직접 생성하지 않고 Spring이 대신 생성
- 기본적으로 Singleton으로 관리 (하나의 객체 공유)
- 객체 재사용 가능 → 메모리 효율적
- 중앙에서 관리 → 유지보수 용이

---

## 📖 15. Bean 등록 방식

### ✔️ 자동 등록
- @Component
- @Service
- @Controller

👉 컴포넌트 스캔을 통해 자동 등록

---

### ✔️ 수동 등록
- @Configuration + @Bean 사용

👉 외부 라이브러리 객체 등록 시 사용

---

## 📖 16. DI (Dependency Injection)

DI는 의존성 주입으로,  
필요한 객체를 직접 생성하지 않고 Spring이 주입해주는 방식이다. 

### ✔️ 장점
- 객체 간 결합도 감소
- 테스트 용이
- 유지보수 편리

---

## 📖 17. Bean 주입 방식

### 1️⃣ 생성자 주입 (권장)
- 생성자를 통해 의존성 주입
- final 사용 가능 → 안정성 높음
- 객체 생성 시점에 의존성 확정

---

### 2️⃣ 필드 주입
- @Autowired 사용
- 코드 간결하지만 테스트 어려움

---

### 3️⃣ Setter 주입
- Setter 메서드로 주입
- 선택적 의존성에 사용

---

## 📖 18. Annotation이란?

Annotation은 코드에 추가적인 의미와 기능을 부여하는 메타데이터이다. 

- 실행 로직에는 직접 포함되지 않음
- 코드의 설정 및 동작을 정의

---

## 📖 19. Annotation 사용 이유

- 코드 간결화
- 반복 코드 제거
- 기능 및 역할 명확화
- Spring이 객체를 자동 관리 (Bean, DI 등)

---

## 📖 20. 주요 Annotation 예시

- @Component → Bean 등록
- @Service → 비즈니스 로직
- @Controller → 요청 처리
- @Configuration → 설정 클래스
- @Bean → 수동 Bean 등록
- @Autowired → 의존성 주입
- @Override → 메서드 재정의

