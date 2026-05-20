# 1. 응답 통일 (Response Unification)
* **개념**: 어플리케이션의 API가 클라이언트(프론트엔드나 외부 서비스)로 보내는 응답 형식을 일관되게 유지하는 작업
* **장점**: 클라이언트가 다양한 API를 호출하더라도 예상 가능한 구조로 응답을 받을 수 있어, 사용성과 유지보수성이 크게 향상됨.
* **응답 통일의 3가지 요소**:
    1. `BaseResponse<T>`: 공통 응답으로 활용할 클래스 생성.
    2. **정상 응답**: Controller에서 응답 반환 시 `BaseResponse.success(...)` 방식으로 통일.
    3. **예외 응답**: 예외 처리에서 응답 반환 시 `BaseResponse.error(...)` 방식으로 통일.

---

# 2. API 흐름 정리

### API 정상 동작 처리 흐름
1. 클라이언트(Client)의 API 요청.
2. 요청은 `Controller` -> `Service` -> `Repository` 순서로 전달되어 최종적으로 `DB`에 도달하여 비즈니스 로직 수행.
3. DB 처리가 완료되면 역순(`Repository` -> `Service` -> `Controller`)으로 데이터 반환됨.
4. `Controller`는 반환할 데이터를 `BaseResponse` 객체에 담아 형식을 통일한 후, 최종적으로 클라이언트에게 응답 전달.

### 예외 처리 동작 흐름
1. 클라이언트의 요청을 처리하는 `Controller`, `Service`, `Repository` 단계 중 어느 곳에서든 예외(Exception) 발생 가능.
2. 발생한 예외는 어플리케이션 전역의 예외를 담당하는 `GlobalExceptionHandler`로 전달됨.
3. 개발자가 정의한 `CustomException`이 발생한 경우, 예외 내부에 포함된 `ErrorCode` 정보도 함께 생성 및 전달됨.
4. `GlobalExceptionHandler`는 이 `ErrorCode`를 활용하여 에러 응답용 `BaseResponse` 객체를 생성함.
5. 최종적으로 통일된 `BaseResponse` 형태의 에러 응답이 클라이언트에게 반환됨.