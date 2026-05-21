## 백엔드 8주차 세션 정리
* * *
> "응답통일과 예외 처리에 대하여."
> -김현수-
### 1. 응답 통일이란?
- 응답 통일은 어플리케이션의 API가 클라이언트로 보내는 응답 형식을 일관되게 유지하는 작업을 의미하는데요.
- 클라이언트는 다양한 API를 호출하더라도 예상 가능한 구조로 응답을 받을 수 있어요.
* * *
### 1-1. 응답 통일의 조건
- `BaseResponse<T>`: 공통 응답으로 활용할 클래스 생성
- Controller에서 응답 반환 시에 `BaseResponse.success(...)` 방식 통일
- 예외 처리에서 응답 반환 시에 `BaseResponse.error(...)` 방식 통일
>"REST 설계 원칙을 따르는 API가 바로 저입니다." -REST API-
* * *
### 2. 프로그래밍 오류
> "컴파일 에러, 런타임 에러, 논리적 에러"
- 컴파일 에러: 컴파일 시에 발생하는 에러
  - 문법 구문 오류
  - IDE에서 바로 에러를 확인할 수 있음
- 런타임 에러: 실행 시에 발생하는 에러
  - 프로그램 실행 중에서 발생함 -> 철저히 대비를 해야 함!
- 논리적 에러: 실행은 되지만 의도와 다르게 동작하는 것

### 2.1 자바의 예외 클래스
> "컴파일 예외 vs 런타임 예외"
- 컴파일 예외(Checked Exception): 반드시 예외 처리를 해야 함
  - IO Exception
  - FileNotFoundException
  - SQLException
- 런타임 예외(Unchecked Exception): 명시적인 처리를 안 해도 됨
  - NullPointerException
  - IllegalArgumentException
  - IndexOutBoundException

### 2.2 예외 처리 핵심 코드
> "BaseErrorCode, GlobalErrorCode, CustomException, GlobalExceptionHandler"

#### BaseErrorCode
- 에러 정보 구조를 정읳는 interface

#### GlobalErrorCode
- 실제 에러 코드 집합(interface의 구현체)
- 상황에 맞는 에러 종류를 정의

#### CustomException
- 최상위 예외 객체를 상속 받음
- 예외 객체(BaseErrorCode)를 품고 있음

#### GlobalExceptionHandler
- 커스텀 예외가 발생했을 때 처리 담당
- 에러 코드를 꺼내서 BaseResponse 생성 후 응답

### 2.3 어노테이션
`@RestControllerAdvice`: 모든 컨트롤러에서 발생하는 예외를 한 곳에서
처리할 수 있게 해주는 전역 예외 처리기
- 클래스에 붙이고, 반환값을 자동으로 JSON 직렬화해줌
`@ExceptionHandler`: 어떤 예외 클래스를 처리할지 지정하는 어노테이션
- `@RestControllerAdvice` 클래스 안의 메서드에 붙이고 인자로 넘긴 예외 타입이 발생하면 해당 메서드 실행

### 2.4 유효성 검사 어노테이션
`@NotNull`: 해당 필드에 null 값 불가
`@NotEmpty`: 해당 필드에 null, "" 불가
`@NotBlank`: 해당 필드에 null, "", " " 불가 (최소 1글자)
`@Min`: 해당 필드의 값이 최소값을 벗어나느지 검증
`@Max`: 해당 필드의 값이 최대값을 벗어나는지 검증
`@Size`: 문자열 최소, 최대 크기 검증, 숫자 최소, 최대값 검증

- 요청 실패 시 재시도 가능 여부 판단 기준
- 멱등하지 않은 경우, 중복 요청으로 문제 발생 가능
- cf. PATCH 요청이 오히려 PUT 요청보다 더 많은 데이터나 복잡한 내용을 포함하게 된다면, 리소스를 전체 교체하는 PUT 방식이 더 적절할 수 있습니다.
***
### 8주차 과제 제출
> 

