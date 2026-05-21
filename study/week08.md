## 응답 통일
- API가 클라이언트에게 보내는 응답 형식을 일관되게 유지하는 작업
- 응답 통일을 하게 되면 각각 다른 API를 호출 하더라도 예상 가능한 응답 구조로 응답 받을 수 있음

### 응답 통일 3가지 요소
- **BaseResponse<T>**
  - 공통 응답으로 활용할 클래스 생성
- **BaseResponse.success()**
  - Controller에서 응답 반환시 (성공)
- **BaseResponse.error()**
  - 예외 처리에서 응답 반환시 (실패)

## 프로그래밍 오류
- **컴파일 에러**
  - 컴파일 시에 발생하는 에러
  - 문법 구문 오류
- **런타임 에러**
  - 실행 시에 발생하는 에러
  - 런타임 에러를 잡는게 제일 중요
  - 프로그램 실행 중에 발생
- **논리적 에러**
  - 실행은 되지만 의도와 다르게 동작하는 것
  - 프로그램이 실행되고 동작하는데는 문제가 없음

### 자바 예외 클래스
- Checked Exception
  - 반드시 예외 처리를 해야됨
  - 종류
    - IOException
    - FileNotFoundException
- Unchecked Exception
  - 명시적인 처리를 안해도 됨
  - Unchecked Exception이 발생하지 않도록 주의해야 함
  - 종류
    - NullPointerException
    - IndexOutOfBoundException

## 예외처리 핵심 파일
- BaseErrorCode
  - 에러 정보 구조를 정의하는 인터페이스
- GlobalErrorCode
  - 실제 에러 코드 집합 (interface 구현체)
- CustomException
  - 최상위 예외 객체 상속 받음
  - 예외 객체 (BaseErrorCode)를 담고 있음
- GlobalExceptionHandler
  - 커스텀 예외가 발생했을 때
  - 유효성 검사에 실패했을 때
  - 예상하지 못한 예외 발생 시
  => BaseResponse 꺼내서 응답

## 예외 처리 종류
- 로컬 예외 처리
- 전역 예외 처리
  - 어플리케이션 전역에서 발생하는 예외를 한 곳에서 처리하고, 응답 포맷을 통일

### 예외 처리 어노테이션
- @RestControllerAdvice
  - 모든 Controller에서 발생하는 예외를 한 곳에서 처리할 수 있게 해주는 전역 예외 처리기
  - 클래스에 붙임
- @ExceptionHandler
  - 어떤 예외 클래스를 처리할지 지정하는 어노테이션
  - @RestControllerAdvice 클래스 안에 붙임 + 인자로 넘긴 예외 타입이 발생하면 메서드가 실행됨

### 유효성 검사
- 사용자가 보낸 데이터가 조건에 맞는지 검사하는 것
- @Valid 어노테이션이 붙었을 때 & DTO에 제약 어노테이션이 있어야 됨
  - 클라이언트가 Controller에 요청을 보냈을 때 요청한 본문이 DTO에 매핑 되면서 유효성 검사가 진행

#### 유효성 검사 어노테이션
- @NotNull
  - 해당 필드에 null 값 불가
- @NotEmpty
  - 해당 필드에 null, "" 불가
- @NotBlank
  - 해당 필드에 null, "", " " 불가
- @Min
  - 해당 필드값이 최소값을 벗어나는지 검증
- @Max
  - 해당 필드값이 최대값을 벗어나는지 검증
- @Size
  - 문자열 최소, 최대 크기 검증
  - 숫자 최소, 최대값 검증


