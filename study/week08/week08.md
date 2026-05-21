# Week 08 - 응답 통일과 예외 처리

## 응답 통일 (Response Unification)

### 개념

API가 클라이언트로 보내는 응답 형식을 **일관되게** 유지하는 것.  
클라이언트는 어떤 API를 호출하든 항상 동일한 구조의 응답을 받을 수 있어 **사용성**과 **유지보수성**이 향상된다.

### 조건

1. `BaseResponse<T>` 공통 응답 클래스 생성
2. Controller에서 `BaseResponse.success(...)` 방식으로 통일
3. 예외 처리에서 `BaseResponse.error(...)` 방식으로 통일

### BaseResponse 구조

```java
public class BaseResponse<T> {
    private boolean success;  // 성공 여부
    private Object code;      // HTTP 상태 코드
    private String message;   // 응답 메시지
    private T data;           // 실제 데이터

    // 성공 응답
    public static <T> BaseResponse<T> success(T data) { ... }
    public static <T> BaseResponse<T> success(String message, T data) { ... }
    public static <T> BaseResponse<T> success(int code, String message, T data) { ... }

    // 에러 응답
    public static <T> BaseResponse<T> error(String code, String message) { ... }
}
```

---

## 프로그래밍 오류 3가지

| 종류 | 설명 | 해결 |
|------|------|------|
| **컴파일 에러** | 컴파일 시 발생 (문법 오류 등) | IDE에서 바로 확인 가능 |
| **런타임 에러** | 실행 중 발생 (NullPointerException 등) | 역추적으로 원인 파악, 사전 대비 필요 |
| **논리적 에러** | 실행은 되지만 의도와 다르게 동작 | 코드/알고리즘 체크로 해결 |

---

## Java 예외 클래스 계층 구조

```
Object
  └── Throwable
        ├── Error (프로그램 코드로 수습 불가 - 메모리 부족, 스택오버플로우)
        └── Exception (프로그램 코드로 수습 가능)
              ├── Checked Exception (IOException, SQLException 등)
              │     - 반드시 예외 처리 필요, 컴파일 단계 확인
              └── RuntimeException (Unchecked Exception)
                    - NullPointerException, IllegalArgumentException 등
                    - 명시적 처리 없어도 됨, 런타임 단계 확인
```

**개발자는 UncheckedException이 발생하지 않도록 주의해야 한다!**

---

## 예외 처리 (Exception Handling)

### 예외 처리의 두 가지 방법

| 방식 | 특징 |
|------|------|
| **로컬 예외 처리** | `try-catch` 사용. 간단한 경우 유용하나 중복 코드, 유지보수 어려움 |
| **전역 예외 처리** | `@RestControllerAdvice` + `@ExceptionHandler`. 한 곳에서 모든 예외 처리, 응답 포맷 통일 |

### 전역 예외 처리 핵심 구성 요소

#### 1. BaseErrorCode (interface)
에러 정보 구조를 정의하는 인터페이스. 모든 ErrorCode enum이 구현한다.

```java
public interface BaseErrorCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
```

#### 2. GlobalErrorCode / 도메인별 ErrorCode (enum)
실제 에러 코드 집합. `BaseErrorCode`를 구현한다.

```java
public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND("POST4041", "해당 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    ...
}
```

#### 3. CustomException
`RuntimeException`을 상속받아 `BaseErrorCode`를 품는 커스텀 예외 클래스.

```java
public class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
```

#### 4. GlobalExceptionHandler
`@RestControllerAdvice`로 전역 예외를 한 곳에서 처리하고 `BaseResponse`로 응답한다.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }
}
```

---

## 예외 처리 흐름

```
Client
  │ 요청
  ▼
Controller ──→ Service ──→ Repository ──→ DB
  │               │
  │         Exception 발생 (CustomException)
  │               │
  │               ▼
  │       GlobalExceptionHandler
  │               │  ErrorCode 꺼내기
  │               ▼
  │          BaseResponse.error(...)
  │               │
  └───────────────┘ 응답
```

**정상 흐름**: Controller → Service → Repository → DB → Service → Controller → `BaseResponse.success(...)` → Client  
**예외 흐름**: Service에서 `CustomException` 발생 → `GlobalExceptionHandler`가 캐치 → `BaseResponse.error(...)` → Client

---

## 유효성 검사 (Validation)

사용자가 보낸 데이터가 조건에 맞는지 검사. 의존성 추가 필요:

```gradle
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

### 동작 방식
1. Controller 메서드의 `@RequestBody` 파라미터에 `@Valid` 추가
2. DTO 클래스 필드에 제약 어노테이션 추가
3. 검사 실패 시 `MethodArgumentNotValidException` 발생 → `GlobalExceptionHandler`가 처리

### 주요 유효성 검사 어노테이션

| 어노테이션 | 설명 |
|-----------|------|
| `@NotNull` | null 불가 |
| `@NotEmpty` | null, `""` 불가 |
| `@NotBlank` | null, `""`, `" "` 불가 (최소 1글자) |
| `@Min(n)` | 최솟값 검증 |
| `@Max(n)` | 최댓값 검증 |
| `@Size(min, max)` | 문자열 길이 / 숫자 범위 검증 |
| `@Email` | 이메일 형식 검증 |
| `@Pattern` | 정규식 검증 |
