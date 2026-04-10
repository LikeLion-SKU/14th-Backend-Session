# 🔗 API 명세서 작성
https://buttercup-cone-687.notion.site/LikeLionSession-API-33e8b324c5c680d3835fc76e1973d209

---

# 📚 3주차 세션 정리

## 🔹 API (Application Programming Interface)

- 소프트웨어 간 데이터를 주고받기 위한 **통신 규칙을 정의한 인터페이스**

---

## 🔹 REST & RESTful API

### ✔ REST (Representational State Transfer)

- 자원(Resource), 행위(Method), 표현(Representation)으로 구성된 설계 방식

### ✔ REST 구성 요소

- **자원 (Resource)**  
  → URI로 표현 (/users, /posts/1)  
  → 서버가 관리하는 데이터

- **행위 (Method)**  
  → HTTP Method 사용 (GET, POST 등)

- **표현 (Representation)**  
  → JSON, XML

---

### ✔ REST 특징

- **Client - Server 구조**
    - 클라이언트: 화면 및 사용자 처리
    - 서버: 데이터 및 비즈니스 로직

- **Stateless (무상태)**
    - 요청마다 필요한 정보 포함

- **Uniform Interface (인터페이스 일관성)**
    - URI + HTTP Method로 규칙 통일

---

### ✔ RESTful API

- REST 설계 원칙을 잘 지킨 API

👉 REST = 개념  
👉 RESTful = 잘 지킨 상태

---

## 🔹 HTTP Method & CRUD

### ✔ CRUD

| 작업 | 의미 |
|---|---|
| Create | 생성 |
| Read | 조회 |
| Update | 수정 |
| Delete | 삭제 |

---

### ✔ HTTP Method

| Method | 역할 |
|---|---|
| GET | 조회 |
| POST | 생성 |
| PUT | 전체 수정 |
| PATCH | 부분 수정 |
| DELETE | 삭제 |

---

### ✔ PUT vs PATCH

- PUT → 전체 수정
- PATCH → 일부 수정  
  👉 차이: 멱등성 (같은 요청 시 결과 동일 여부)

---

## 🔹 REST 설계 원칙

- 명사 사용 (/posts)
- 복수형 사용 (/posts)
- 소문자 사용 (/users)
- 마지막 `/` 사용 X
- 계층 구조 표현 (/users/1/posts)
- 확장자 사용 X (/photos.jpg ❌)

---

## 🔹 API 명세서

### ✔ 정의

- API의 동작 방식과 사용 방법을 정리한 문서

### ✔ 필요성

- 개발자 간 명확한 소통
- API 설계의 일관성 유지
- 유지보수 용이

---

### ✔ 구성 요소

- API 이름 / 버전 / Base URL
- Endpoint & HTTP Method
- 요청 (Path, Query, Header, Body)
- 응답 (JSON, 상태 코드)
- 에러 처리

---

## 🔹 요청 & 응답 구조

### ✔ 요청 구성 요소

- **Path Variable** → `/posts/{postId}`
- **Query Param** → `?page=0&size=10`
- **Header** → Authorization
- **Body** → JSON 데이터

---

### ✔ 응답 구성

- JSON 형식
- 상태 코드 (200, 201, 404, 500 등)
- 응답 예시 제공

---

## 🔹 API 문서화 도구

- Swagger
- Postman
- GitBook
- Spring REST Docs

---

## 🔹 Java vs Spring 객체 관리

### ✔ Java의 객체 생성 방식

- `new` 키워드로 직접 생성
- 객체 중복 생성 → 메모리 낭비
- 의존 관계 직접 설정 → 결합도 증가
- 유지보수 어려움

---

### ✔ Spring의 객체 관리 방식

- Spring Container가 객체 생성 및 관리
- 관리되는 객체 → **Bean**

### ✔ 특징 및 장점

- 싱글톤 방식으로 객체 재사용
- 객체를 중앙에서 관리
- 유지보수 용이
- 코드 변경 영향 최소화

---

## 🔹 Bean & DI

### ✔ Bean

- Spring이 관리하는 객체

---

### ✔ DI (Dependency Injection)

- 객체를 직접 생성하지 않고  
  **Spring이 대신 주입**

### ✔ 장점

- 결합도 감소
- 테스트 용이
- 유지보수 편리

---

## 🔹 Bean 주입 방식

### ✔ 생성자 주입 (권장 ⭐)

- 생성자를 통해 의존성 주입
- `final` 사용 가능 → 안정성 높음

---

### ✔ 필드 주입

- `@Autowired` 사용
- 간단하지만 테스트 어려움

---

### ✔ Setter 주입

- 선택적 의존성에 사용

---

## 🔹 Annotation (@)

- 코드에 기능을 부여하는 설정

### ✔ 주요 Annotation

- `@Component` → Bean 자동 등록
- `@Configuration` → 설정 클래스
- `@Bean` → 수동 등록
- `@Transactional` → 트랜잭션 처리
- `@Value` → 외부 값 주입

---

## 🔹 Lombok

- 반복 코드 자동 생성 라이브러리

### ✔ 주요 Annotation

- `@Getter`, `@Setter`
- `@Builder`
- `@NoArgsConstructor`
- `@AllArgsConstructor`
- `@RequiredArgsConstructor`

---

## 🔹 Transaction (@Transactional)

- 여러 DB 작업을 하나의 단위로 처리

### ✔ 동작

- 성공 → Commit
- 실패 → Rollback

👉 데이터 일관성 유지

---

## 🔹 ACID 특성

- **Atomicity (원자성)** → 모두 성공 or 실패
- **Consistency (일관성)** → 항상 정상 상태 유지
- **Isolation (격리성)** → 트랜잭션 간 영향 없음
- **Durability (지속성)** → 결과 영구 저장
