# 🔐 Spring Boot에서 Access Token과 Refresh Token

## 🔹 Access Token과 Refresh Token이란?

- Spring Boot에서 JWT 로그인을 구현할 때 보통 **Access Token**과 **Refresh Token**을 함께 사용함
- Access Token은 사용자가 API를 요청할 때 인증된 사용자인지 확인하는 토큰
- Refresh Token은 Access Token이 만료되었을 때 새로운 Access Token을 발급받기 위한 토큰
- OAuth 2.0 공식 문서에서도 Refresh Token은 Access Token을 새로 얻기 위한 자격 정보라고 설명함

> 즉, Access Token은 “API 접근용 토큰”, Refresh Token은 “Access Token 재발급용 토큰”

## 🔹 Access Token의 역할

### ✔ Access Token이란?

- 로그인 성공 후 서버가 클라이언트에게 발급하는 토큰
- 사용자가 API를 요청할 때 `Authorization` 헤더에 담아서 보냄
- 서버는 Access Token을 검증한 뒤, 사용자가 인증된 사용자인지 확인함

---
# 📚 7주차 세션 정리
## 🔹 인증이 왜 필요할까?

- 서버는 요청을 받았을 때 **요청을 보낸 사용자가 누구인지** 알아야 함
- 로그인한 사용자인지, 어떤 권한을 가진 사용자인지 확인해야 API를 안전하게 제공할 수 있음
- 인증과 인가를 통해 사용자가 접근할 수 있는 기능을 구분할 수 있음

### ✔ 예시

- 로그인 성공 여부 확인
- 마이페이지 접근 가능 여부 확인
- 관리자 페이지 접근 가능 여부 확인

---

## 🔹 인증 Authentication

### ✔ 인증이란?

- 사용자의 신원을 확인하는 과정
- 쉽게 말해 **“너 누구야?”** 를 확인하는 것
- 사용자가 입력한 아이디와 비밀번호가 실제 DB에 저장된 사용자 정보와 일치하는지 확인함

| 상황 | 설명 |
|---|---|
| 로그인 성공 | 이메일과 비밀번호가 일치하여 사용자 신원 확인 완료 |
| 마이페이지 접근 | 로그인한 사용자만 접근 가능 |
| 토큰 만료 | 인증 정보가 유효하지 않아 401 응답 |

---

## 🔹 인가 Authorization

### ✔ 인가란?

- 인증된 사용자가 특정 기능에 접근할 권한이 있는지 확인하는 과정
- 쉽게 말해 **“너 이거 할 권한 있어?”** 를 확인하는 것
- 인가는 인증이 먼저 완료된 사용자를 대상으로 진행됨

### ✔ 인가 실패 시

- **403 Forbidden**
- 로그인은 했지만 권한이 부족한 경우
- 일반 사용자가 관리자 페이지에 접근하려는 경우

| 상황 | 설명 |
|---|---|
| 마이페이지 접근 | 인증된 사용자만 가능 |
| 관리자 페이지 접근 | 인증된 사용자 + 관리자 권한 필요 |
| 권한 부족 | 인증은 됐지만 필요한 권한이 없어 403 응답 |

---

## 🔹 인증과 인가 비교

| 구분 | 인증 Authentication | 인가 Authorization |
|---|---|---|
| 의미 | 사용자가 누구인지 확인 | 사용자가 권한을 가졌는지 확인 |
| 질문 | 너 누구야? | 너 이거 할 권한 있어? |
| 선행 조건 | 없음 | 인증 완료 필요 |
| 실패 응답 | 401 Unauthorized | 403 Forbidden |
| 예시 | 로그인 실패 | 일반 사용자가 관리자 페이지 접근 |

---

# 🔹 Spring Security란?

- Spring에서 인증과 인가를 처리해주는 보안 프레임워크
- Controller에 요청이 도달하기 전에 보안 검사를 수행함
- 로그인 여부, 권한 여부, JWT 검증 등을 직접 모든 API마다 작성하지 않아도 됨

### ✔ 직접 처리한다면?

```java
if (!isLoginUser()) {
    throw new UnauthorizedException();
}

if (!isAdmin()) {
    throw new ForbiddenException();
}
```

## ✔ Spring Security를 사용하면?

- 요청이 Controller에 도달하기 전에 **Security Filter**가 요청을 가로챔
- 인증 여부를 검사함
- 권한이 있는지 확인함
- 성공하면 Controller로 요청을 전달함
- 실패하면 `401` 또는 `403` 예외를 반환함

---

# 🔹 Spring Security의 핵심 구조

## 1. Security Filter Chain

- 보안 관련 필터들이 연속적으로 실행되는 구조
- 요청이 Controller에 도달하기 전에 여러 보안 검사를 수행함

### ✔ 처리 가능한 기능

- CORS 검사
- CSRF 검사
- 로그인 처리
- JWT 검증
- 권한 확인
- 예외 처리

---

## 2. AuthenticationFilter

- 로그인 요청을 가로채는 필터
- 사용자가 보낸 아이디와 비밀번호를 꺼냄
- 인증되지 않은 `Authentication` 객체를 생성함

### ✔ 쉽게 말하면

> 로그인 요청에서 아이디와 비밀번호를 꺼내서  
> “이 사용자 인증 좀 해줘”라고 넘기는 역할

---

## 2. UsernamePasswordAuthenticationToken

### ✔ 특징

- 아직 인증되지 않은 상태의 인증 객체
- 사용자가 입력한 아이디와 비밀번호를 담고 있음

---

## 🔹 3. AuthenticationManager

- 실제 인증 처리를 담당할 Provider를 찾는 역할
- 현재 인증 방식을 처리할 수 있는 `AuthenticationProvider`에게 인증을 위임함

### ✔ 쉽게 말하면

```text
Filter: 아이디와 비밀번호 가져왔으니 인증 좀 해줘.
Manager: 이 인증 방식 처리할 수 있는 Provider에게 맡길게.
```

---

## 🔹 4. AuthenticationProvider

`AuthenticationProvider`는 **실제 인증 로직을 수행하는 객체**이다.

`UserDetailsService`를 호출해 DB에서 사용자 정보를 가져오고, 사용자가 입력한 비밀번호와 DB에 저장된 암호화 비밀번호를 비교한다.

### ✔ 주요 역할

- 사용자 조회
- 비밀번호 검증
- 권한 정보 확인
- 인증 성공 객체 생성

---

## 🔹 5. UserDetailsService

`UserDetailsService`는 **DB에서 사용자 정보를 조회하는 역할**을 한다.

프로젝트마다 `User` 엔티티 구조가 다르기 때문에 Spring Security는 공통 규격인 `UserDetails`를 사용한다.

## 🔹 6. UserDetails

`UserDetails`는 Spring Security가 이해할 수 있는 사용자 정보 객체이다.

Spring Security는 특정 프로젝트의 `User` 엔티티를 직접 알 수 없기 때문에, 공통 인터페이스인 `UserDetails`를 기준으로 사용자 정보를 다룬다.

### ✔ 포함 가능한 정보

- 사용자 식별자
- 로그인 아이디
- 암호화된 비밀번호
- 권한 목록
- 계정 만료 여부
- 계정 잠김 여부

---

## 🔹 7. PasswordEncoder

`PasswordEncoder`는 비밀번호를 안전하게 암호화하고 비교하는 객체이다.

### ✔ 회원가입 시

사용자가 입력한 비밀번호를 암호화하여 DB에 저장한다.

```java
1234 → 암호화된 비밀번호
```
## 🔹 8. SecurityContextHolder

`SecurityContextHolder`는 **인증이 성공한 사용자 정보를 저장하는 공간**이다.

인증 성공 후 `Authentication` 객체가 `SecurityContextHolder`에 저장된다.

이후 `Controller`나 `Service`에서 현재 로그인한 사용자를 확인할 수 있다.

---

### ✔ 저장되는 정보

- `Authentication`

---

### ✔ Authentication에 들어가는 정보

| 구성 요소 | 설명 |
|---|---|
| Principal | 인증된 사용자 정보 |
| Credentials | 자격 증명, 보통 비밀번호 |
| Authorities | 사용자 권한 정보 |

---

# 🔄 Spring Security 인증 처리 과정

## 1. 클라이언트 로그인 요청

클라이언트가 로그인 API로 아이디와 비밀번호를 전송한다.

### 🔹 2. AuthenticationFilter가 요청 처리

`AuthenticationFilter`가 로그인 요청을 가로챈다.

요청에서 `username`과 `password`를 꺼낸 뒤,  
`UsernamePasswordAuthenticationToken`을 생성한다.

이때 생성되는 객체는 아직 인증되지 않은 `Authentication` 객체이다.

---

### 🔹 3. AuthenticationManager에게 인증 요청

`AuthenticationFilter`가 `AuthenticationManager`에게 인증을 요청한다.

## 🔹 4. AuthenticationProvider에게 위임

`AuthenticationManager`는 현재 인증 방식을 처리할 수 있는  
`AuthenticationProvider`를 찾는다.

그리고 적절한 `Provider`에게 인증을 위임한다.

## 🔹 5. UserDetailsService 호출

AuthenticationProvider가 UserDetailsService를 호출한다.
UserDetailsService는 DB에서 사용자 정보를 조회한다.

```java
userDetailsService.loadUserByUsername(username);
```
## 🔹 6. UserDetails 반환

DB에서 조회한 사용자 정보를 UserDetails 형태로 반환한다.
Spring Security는 UserDetails를 기준으로 인증을 진행한다.

```java
UserDetailsService → Provider
```
## 🔹 7. 비밀번호 검증

Provider가 사용자가 입력한 비밀번호와
DB에 저장된 암호화 비밀번호를 비교한다.
이때 PasswordEncoder를 사용한다.

```java
passwordEncoder.matches(inputPassword, userDetails.getPassword());
```

## 🔹 8. 인증 성공 결과 반환

비밀번호가 일치하면 인증에 성공한다.
AuthenticationProvider가 인증된 Authentication 객체를
AuthenticationManager에게 반환한다.

```java
Provider → Manager
```

## 🔹 9. Filter에게 인증 결과 반환
AuthenticationManager가 인증 결과를 AuthenticationFilter에게 반환한다.

```java
Manager → Filter
```

## 🔹 10. SecurityContextHolder에 저장

AuthenticationFilter가 인증된 사용자 정보를
SecurityContextHolder에 저장한다.
이후 Controller와 Service에서 현재 로그인한 사용자를 사용할 수 있다.

```java
SecurityContextHolder 저장 완료
```

## Spring Security 인증 흐름 요약

```text
[1] 클라이언트 로그인 요청
        ↓
[2] AuthenticationFilter가 username/password 추출
        ↓
[3] UsernamePasswordAuthenticationToken 생성
        ↓
[4] AuthenticationManager에게 인증 요청
        ↓
[5] AuthenticationProvider에게 위임
        ↓
[6] UserDetailsService가 DB에서 사용자 조회
        ↓
[7] UserDetails 반환
        ↓
[8] PasswordEncoder로 비밀번호 검증
        ↓
[9] 인증 성공 시 Authentication 객체 생성
        ↓
[10] SecurityContextHolder에 인증 정보 저장
```

## 🔹 Token 설계 시 보안 고려사항
### 1. Access Token 만료 시간은 짧게 설정하기
-Access Token은 API 요청마다 사용됨
-탈취되면 만료 전까지 API 접근이 가능함
-따라서 만료 시간을 짧게 설정하는 것이 좋음

### 2. Refresh Token은 서버에서 관리하기
-Refresh Token은 Access Token을 재발급할 수 있는 중요한 토큰
-서버에서 DB 또는 Redis를 통해 관리하는 것이 좋음
#### ✔ 서버 저장이 필요한 이유
-로그아웃 시 Refresh Token 삭제 가능
-탈취된 토큰 차단 가능
-재발급 요청 시 저장된 토큰과 비교 가능

### 3. 토큰에는 민감한 정보를 넣지 않기
-JWT의 Payload는 누구나 디코딩해서 볼 수 있음
-따라서 민감한 정보는 절대 넣으면 안 됨

### 4. Secret Key는 안전하게 관리하기
-JWT Signature는 Secret Key를 기반으로 생성됨
-Secret Key가 노출되면 공격자가 위조 토큰을 만들 수 있음
-코드에 직접 작성하지 않고 환경변수로 관리하는 것이 좋음

### 5. 로그아웃 시 Refresh Token 삭제하기
-로그아웃하면 서버에 저장된 Refresh Token을 삭제해야 함
그래야 로그아웃 후 같은 Refresh Token으로 Access Token을 다시 발급받을 수 없음

```text
사용자 로그아웃 요청
        ↓
서버에서 Refresh Token 삭제
        ↓
이후 재발급 요청 실패
        ↓
다시 로그인 필요
```
### 6. HTTPS 사용하기
-Access Token과 Refresh Token은 네트워크를 통해 전달됨
-HTTP를 사용하면 중간에서 토큰이 탈취될 위험이 있음
-실제 배포 환경에서는 HTTPS를 사용해야