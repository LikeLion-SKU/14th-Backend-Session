# 📚 8주차 세션 정리

## 🔹 응답 통일

### ✔ 응답 통일이란?

- 애플리케이션의 API가 클라이언트에게 보내는 **응답 형식을 일관되게 유지하는 작업**
- 프론트엔드나 외부 서비스는 어떤 API를 호출하더라도 예측 가능한 구조로 응답을 받을 수 있음
- 응답 구조가 통일되면 API 사용성, 유지보수성, 협업 효율이 좋아짐

---

## 🔹 응답 통일이 필요한 이유

- API마다 응답 형식이 다르면 프론트엔드에서 매번 다른 방식으로 데이터를 처리해야 함
- 성공 응답과 실패 응답의 구조가 다르면 에러 처리도 복잡해짐
- 공통 응답 클래스를 사용하면 성공/실패 여부, 상태 코드, 메시지, 데이터를 한 가지 형식으로 관리할 수 있음

### ✔ 예시

| 상황 | 설명 |
|---|---|
| 게시글 생성 성공 | `success`, `code`, `message`, `data` 형식으로 응답 |
| 로그인 성공 | 토큰 값을 `data` 안에 담아 응답 |
| 예외 발생 | `BaseResponse.error(...)` 형식으로 실패 응답 반환 |

---

## 🔹 응답 통일의 조건

응답 통일을 위해서는 다음 3가지 요소가 필요함

| 요소 | 설명 |
|---|---|
| `BaseResponse<T>` | 성공/실패 응답을 공통으로 감싸는 클래스 |
| `BaseResponse.success(...)` | Controller에서 성공 응답을 반환할 때 사용 |
| `BaseResponse.error(...)` | 예외 처리에서 실패 응답을 반환할 때 사용 |

---

## 🔹 API 정상 동작 처리 흐름

### ✔ 정상 요청 흐름

1. Client가 Controller에 요청을 보냄
2. Controller가 요청 데이터를 받음
3. Service에서 비즈니스 로직을 처리함
4. Repository가 DB와 데이터를 주고받음
5. 처리 결과를 다시 Controller로 반환함
6. Controller가 `BaseResponse.success(...)`로 응답을 통일해서 Client에게 반환함

### ✔ 흐름 정리

| 단계 | 역할 |
|---|---|
| Client | API 요청을 보냄 |
| Controller | 요청을 받고 응답을 반환 |
| Service | 비즈니스 로직 처리 |
| Repository | DB 접근 |
| DB | 데이터 저장 및 조회 |
| BaseResponse | 응답 형식 통일 |

---

## 🔹 BaseResponse

### ✔ BaseResponse란?

- API 응답 형식을 통일하기 위해 사용하는 공통 응답 클래스
- 성공 응답과 실패 응답을 같은 구조로 반환할 수 있게 해줌
- Controller와 GlobalExceptionHandler에서 공통으로 사용됨

### ✔ 성공 응답 예시

```json
{
  "success": true,
  "code": 200,
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "accessToken": "토큰값"
  }
}
```
---

## 🔹 프로그래밍 오류

### ✔ 프로그래밍 오류 3가지
| 종류     | 설명                      |
| ------ | ----------------------- |
| 컴파일 에러 | 컴파일 시점에 발생하는 에러         |
| 런타임 에러 | 프로그램 실행 중 발생하는 에러       |
| 논리적 에러 | 실행은 되지만 의도와 다르게 동작하는 에러 |

---

## 🔹 컴파일 에러

### ✔ 컴파일 에러란?
-컴파일 단계에서 발견되는 에러
-대표적으로 문법 오류, 타입 오류 등이 있음
-컴파일 자체가 되지 않기 때문에 문제를 바로 확인할 수 있음
-IDE에서 대부분 빨간 줄로 확인 가능

---

## 🔹 런타임 에러

### ✔ 런타임 에러란?
-프로그램 실행 중에 발생하는 에러
-컴파일은 성공했지만 실행 도중 문제가 발생하는 경우
-개발자가 원인을 추적해서 해결해야 함
-name이 null인데 length()를 호출하면 NullPointerException 발생

---

## 🔹 논리적 에러

### ✔ 논리적 에러란?
-프로그램은 정상 실행되지만 의도와 다르게 동작하는 오류
-흔히 말하는 버그에 해당함
-코드나 알고리즘을 확인해서 해결해야 함

---

## 🔹 자바의 예외 클래스

### ✔ Throwable 구조
| 구분        | 설명                       |
| --------- | ------------------------ |
| Error     | 프로그램 코드로 수습하기 어려운 심각한 오류 |
| Exception | 프로그램 코드로 수습 가능한 예외 상황    |

---

## 🔹 Error

### ✔ Error란?
-외부적인 요인이나 시스템 문제로 발생하는 심각한 오류
-프로그램 코드로 직접 수습하기 어려움
-일반적인 비즈니스 로직에서 직접 처리하지 않음

---

## 🔹 Exception

### ✔ Exception이란?
-프로그램 코드로 처리할 수 있는 예외 상황
-로직 실수나 사용자 입력 문제 등으로 발생할 수 있음
-서비스에서 발생할 수 있는 문제는 예외 처리를 통해 응답을 통일해야 함

---

## 🔹 Checked Exception과 Unchecked Exception

### ✔ Checked Exception

- 컴파일 시점에 확인되는 예외
- 반드시 예외 처리를 해야 함
- 처리하지 않으면 컴파일이 되지 않음

### ✔ Unchecked Exception

- 런타임 시점에 발생하는 예외
- 명시적으로 예외 처리를 하지 않아도 컴파일은 가능함
- 개발자는 이런 예외가 발생하지 않도록 주의해야 함

| 구분 | Checked Exception | Unchecked Exception |
|---|---|---|
| 확인 시점 | 컴파일 단계 | 런타임 단계 |
| 예외 처리 | 반드시 처리 필요 | 명시적 처리 필수 아님 |
| 예시 | `IOException`, `SQLException` | `NullPointerException`, `IllegalArgumentException` |

---

## 🔹 비즈니스 로직 예외 처리

### ✔ 비즈니스 로직 예외란?

- 서비스 흐름상 발생할 수 있는 논리적인 예외 상황
- 문법 오류가 아니라, 서비스 규칙에 맞지 않는 요청이 들어왔을 때 발생함

### ✔ 예시

| 상황 | 예외 |
|---|---|
| 존재하지 않는 게시글 조회 | 게시글을 찾을 수 없음 |
| 중복 이메일로 회원가입 | 이미 사용 중인 이메일 |
| 로그인 비밀번호 불일치 | 이메일 또는 비밀번호가 올바르지 않음 |
| 잘못된 요청 데이터 | 유효하지 않은 입력값 |

---

## 🔹 예외 처리 방법

- 예외 처리 방법은 크게 **로컬 예외 처리**와 **전역 예외 처리**로 나눌 수 있음

| 구분 | 설명 |
|---|---|
| 로컬 예외 처리 | 특정 메서드 안에서 `try-catch`로 처리 |
| 전역 예외 처리 | 애플리케이션 전체 예외를 한 곳에서 처리 |

---

## 🔹 로컬 예외 처리

### ✔ 로컬 예외 처리란?

- 예외가 발생할 수 있는 코드 주변에서 직접 처리하는 방식
- 간단한 경우에는 유용함
- 하지만 여러 곳에서 반복되면 코드 중복이 많아지고 유지보수가 어려워짐

```java
try {
    // 예외가 발생할 수 있는 코드
} catch (Exception e) {
    // 예외 발생 시 처리 로직
}
```
---
## 🔹 전역 예외 처리

### ✔ 전역 예외 처리란?

- 애플리케이션 전체에서 발생하는 예외를 한 곳에서 처리하는 방식
- Spring에서는 `@RestControllerAdvice`와 `@ExceptionHandler`를 사용함
- 예외 응답 형식을 `BaseResponse.error(...)`로 통일할 수 있음

### ✔ 전역 예외 처리에 필요한 요소

| 요소 | 설명 |
|---|---|
| `BaseErrorCode` | 에러 코드의 공통 규격 |
| `CustomException` | 비즈니스 예외 발생 시 사용하는 커스텀 예외 |
| `GlobalExceptionHandler` | 전역에서 예외를 처리하는 클래스 |
| `@RestControllerAdvice` | 모든 Controller 예외를 한 곳에서 처리 |
| `@ExceptionHandler` | 특정 예외 타입을 처리할 메서드 지정 |

---

## 🔹 예외 처리 흐름

### ✔ 예외 발생 흐름

1. Client가 API 요청을 보냄
2. Controller, Service, Repository 중 하나에서 예외가 발생함
3. 비즈니스 예외라면 `CustomException`이 발생함
4. `GlobalExceptionHandler`가 예외를 잡음
5. 예외에 맞는 `ErrorCode`를 확인함
6. `BaseResponse.error(...)` 형식으로 응답을 통일함
7. Client에게 실패 응답을 반환함

---

## 🔹 CustomException

### ✔ CustomException이란?

- 프로젝트에서 직접 만든 예외 클래스
- 비즈니스 로직에서 발생하는 예외를 처리할 때 사용함
- 내부에 `BaseErrorCode`를 가지고 있어서 어떤 에러인지 구분할 수 있음

```java
throw new CustomException(PostErrorCode.POST_NOT_FOUND);
```
---

## 🔹 ErrorCode

### ✔ ErrorCode란?
-예외 상황별 코드, 메시지, HTTP 상태를 정의하는 enum
-도메인별로 나누어 관리할 수 있음
-예외 응답을 일관되게 반환하기 위해 사용함

---
## 🔹 GlobalExceptionHandler

### ✔ GlobalExceptionHandler란?

- 전역 예외 처리 클래스
- Controller, Service 등에서 발생한 예외를 한 곳에서 처리함
- 예외 발생 시 `BaseResponse.error(...)` 형식으로 응답을 반환함

---

## 🔹 유효성 검사

### ✔ 유효성 검사란?

- 사용자가 보낸 데이터가 조건에 맞는지 검사하는 것
- Spring에서는 Controller의 요청 DTO에 `@Valid`를 붙이면 자동으로 검사함
- DTO 필드에는 `@NotBlank`, `@NotNull`, `@Size` 같은 제약 어노테이션을 붙임

---

## 🔹 유효성 검사 조건

유효성 검사가 동작하려면 다음 조건이 필요함

| 조건 | 설명 |
|---|---|
| validation 의존성 추가 | `spring-boot-starter-validation` 필요 |
| Controller에 `@Valid` 작성 | 요청 DTO 검증 실행 |
| DTO 필드에 제약 어노테이션 작성 | 필드별 검증 조건 지정 |

---

## 🔹 유효성 검사 흐름

### ✔ 흐름 정리

1. Client가 요청 데이터를 보냄
2. Controller의 `@Valid`가 DTO 검증을 실행함
3. DTO 필드의 제약 조건을 검사함
4. 조건을 만족하지 못하면 `MethodArgumentNotValidException` 발생
5. `GlobalExceptionHandler`가 예외를 처리함
6. `BaseResponse.error(...)`로 실패 응답을 반환함

---

## 🔹 @RestControllerAdvice

### ✔ @RestControllerAdvice란?

- 모든 Controller에서 발생하는 예외를 한 곳에서 처리할 수 있게 해주는 전역 예외 처리 어노테이션
- 클래스에 붙여서 사용함
- 반환값을 자동으로 JSON 형태로 변환해줌

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
}
```
---

## 🔹 @ExceptionHandler

### ✔ @ExceptionHandler란?
-어떤 예외 클래스를 처리할지 지정하는 어노테이션
-@RestControllerAdvice 클래스 안의 메서드에 붙임
-지정한 예외 타입이 발생하면 해당 메서드가 실행됨

```java
@ExceptionHandler(CustomException.class)
public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    BaseErrorCode errorCode = ex.getErrorCode();

    return ResponseEntity.status(errorCode.getStatus())
            .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
}
```