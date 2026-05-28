# 7주차 - Spring Security와 JWT

># 응답 통일   
>## 응답 통일의 조건
>>### 1. BaseResponse<T> : 공통 응답에 사용할 클래스
>>### 2. Controller에서 응답 반환 시에 BaseResponse.success(..) 방식 통일
>>### 3. 예외 처리에서 응답 반환 시에 BaseResponse.error(..) 방식 통일)
>
>># BaseResponse 구성
>>### 1. 성공 여부를 알려주는 불린형태의 필드
>>### 2. HTTP 상태코드 저장용 필드
>>### 3. 응답 메시지 저장 필드
>>### 4. 응답 데이터 필드(제네릭 문법 활용)


>## 프로그래밍 오류
>>## 컴파일 에러
>>### 컴파일 시에 발생하는 에러
>>### ex) 문법 구문 오류
>>### 해결 법 -> IDE에서 에러 체크(컴파일이 되지 않는 것이기에 바로 확인 가능)
>
>>## 런타임 에러
>>### 실행 시에 발생하는 에러
>>### 해결 법 -> 역추적해서 원인을 확인, 그 코드를 수정하여 해결
>
>>## 논리적 에러
>>### 실행은 되지만, 의도와 다르게 동작하는 것(버그)
>>### 해결 법 -> 코드와 알고리즘 체크로 해결

># 자바에서의 오류를 대비하는 방법
>># 자바의 예외 클래스
>>>## 컴파일 예외(Checked Exception)
>>>### C반드시 예외 처리를 해야함(안 하면 컴파일 X)
>>>### 컴파일 단계에서 확인
>>>### ex) IOException, FileNotFoundException, SQLException
>
>>>## 런타임 예외(Unchecked Exception)
>>>### 명시적인 처리 안 해도 가능
>>>### 런타임 단계에서 확인
>>>### ex) NullPointerException, IllegalArgumentException, IndexOutOfBoundException

># 스프링에서의 예외 처리 핵심 코드
>>## 1. BaseErrorCode
>>## 에러 정보 구조를 정의하는 interface
>
>>## 2. GlobalErrorCode
>>## 실제 에러 코드 집합(interface의 구현체)
>>## ENUM 형태로 구현하여 사용
>
>>## 3. CustomException
>>## 최상위 예외 객체를 상속 받음
>>## 생성자 주입으로 BaseErrorCode 사용
>
>>## 4. GlobalExceptionHandler
>>## 커스텀 예외가 발생했을 때 처리 담당
>>## 에러 코드를 꺼내서 BaseResponse 생성 후 응답
>
>>## 예외 처리시 사용하는 어노테이션 정리
>>## 1. RestControllerAdvice
>>## 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리할 수 있게 해주는 전역 예외 처리기
>>## 특징) 반환 값 JSON 자동 직렬화, GlobalExceptionHandler(클래스)에 사용
>>## 2. ExceptionHandler
>>## 어떤 예외 클래스를 처리할지 지정하는 어노테이션
>>## (속성명으로 예외 클래스를 지정)
>>## RestControllerAdvice 클래스 안, 메소드에 사용 -> 그 메소드는 예외 발생시 처리하는 로직 가지고 있음

># 스프링에서의 유효성 검사
>>## 사용자가 보낸 데이터가 조건에 맞는지 검사하는 것
>>## Spring에서는 클라이언트가 Controller에 요청을 보냈을 때, 요청 본문이 DTO에 매핑되면서 유효성 검사가 진행된다.
>
>>## 유효성 검사의 조건
>>### 1. Controller의 요청 본문 DTO에 @Valid 어노테이션이 붙어야 함
>>### 2. DTO 클래스에 제약 어노테이션이 있어야함
>>### 위 두 조건 만족 시 Spring이 자동으로 유효성 검사 진행
>>### 실패 시 MethodArgumentNotValidException 발생
>
>>## 유효성 검사 어노테이션
>>>## 1. @NotNull: 해당 필드에 null값 불가
>>
>>>## 2. @NotEmpty: 해당 필드에 null, "" 불가
>>
>>>## 3. @NotBlank: 해당 필드에 null, "", " " 불가
>>
>>>## 4. @Min: 해당 필드의 값이 최소값을 벗어나는지 검증
>>
>>>## 5. @Max: 해당 필드의 값이 최댓값을 벗어나는지 검증
>>
>>>## 6. @Size: 문자열 최소, 최대 크기 검증 + 숫자 최소, 최댓값 검증



>## Spring Security 인증 처리 과정
