# ✅ 응답 통일 & 예외 처리 정리

---

## 📌 응답 통일

- api가 클라이언트로 보내는 응답 형식을 일관되게 유지하는 작업

---

## ✅ 응답 통일의 조건

1. `BaseResponse<T>` : 공통 응답으로 활용할 클래스 생성
2. Controller에서 응답 반환 시에 `BaseResponse.success(…)` 방식 통일
3. 예외 처리에서 응답 반환 시에 `BaseResponse.error(…)` 방식으로 통일

---

# ⚠️ 프로그래밍 오류

- 컴파일 에러 : 컴파일 시 발생하는 에러
- 런타임 에러 : 실행 시에 발생하는 에러
- 논리적 에러 : 실행은 되지만 의도와 다르게 동작하는 것

---

## 🧩 논리적 에러

- 버그의 일종
- 프로그램이 실행하고 작동하는데 문제 X
- 의도와 다른 동작을 수행하여 서비스에 지장

> → 코드와 알고리즘 체로 해결

---

## 🛠️ 컴파일 에러

- 컴파일 단계에서 발견되는 에러
- 문제 식별 바로 가능

> → IDE에서 에러 체크

---

## 💥 런타임 에러

- 프로그램 실행 중에 발생하는 에러
- 프로그래머가 역추적해서 원인을 확인
- 실행 도중 발생할 수 있는 런타임 에러를 고려하여 철저히 대비

---

# ☕ 자바의 예외 클래스

## ✅ Checked Exception

- 반드시 예외 처리함
- 컴파일 단계에서 확인
- `IOException`
- `FileNotFoundException`
- `SQLException`

---

## ✅ Unchecked Exception

- 명시적인 처리를 안 해도 됨
- 런타임 단계에서 확인
- `NullPointerException`
- `IllegalArgumentException`
- `IndexOutOfBoundException`

---

# 🧯 예외 처리

## 📍 로컬 예외 처리

```java
try {
    // 예외가 발생할 수 있는 코드
} catch (Exception e) {
    // 예외가 발생했을 때 처리 로직
}
```

---

## 🌐 전역 예외 처리

```java
- BaseErrorCode
- CustomException
- @RestControllerAdvice
```

---

## 🧩 @RestControllerAdvice

- 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리
- 클래스 붙이기, 반환값 자동으로 JSON 직렬화

---

## 🧩 @ExceptionHandler

- 어떤 예외 클래스를 처리할지 지정
- 클래스 안에 매서드 붙이기, 인자로 넘긴 예외 타입 발생 → 매서드 실행

---

# ✅ 대표적 예외 처리

- Controller의 요청 본문 DTO에 `@Valid` 어노테이션이 붙이기
- DTO 클래스에 제약 어노테이션이 필수

---

# 🧪 유효성 검사 어노테이션

| 어노테이션 | 설명 |
| --- | --- |
| `@NotNull` | 해당 필드 null값 불가 |
| `@NotEmpty` | 해당 필드에 null, `""` 불가 |
| `@NotBlank` | 해당 필드에 null, `""`, `" "` 불가 |
| `@Min` | 해당 필드의 값이 최소값을 벗어나는지 검증 |
| `@Max` | 해당 필드의 값이 최대값을 벗어나는지 검증 |
| `@Size` | 문자열 최소, 최대 크기 검증 / 숫자 최소, 최대값 검증 |