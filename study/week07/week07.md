# Spring Security와 JWT 정리

## 1. 인증(Authentication)과 인가(Authorization)
* **인증(Authentication)**: "너 누구야?" 요청을 보낸 사용자의 신원이 진짜인지 확인하는 행위임.
    * 실패 시 **401 Unauthorized** (비로그인 상태) 에러 반환.
* **인가(Authorization)**: "너 이거 할 권한 있어?" 인증된 사용자에게 접근 권한을 부여하고 검증하는 행위임.
    * 실패 시 **403 Forbidden** (권한 부족) 에러 반환.

## 2. Spring Security
* Spring에서 인증과 인가를 처리해 주는 보안 프레임워크.
* API 요청이 Controller에 도달하기 전에 가로채서 보안 검사를 수행함.

### Spring Security 인증 처리 과정
1. 클라이언트 로그인 요청 (POST /login).
2. `AuthenticationFilter`가 `UsernamePasswordAuthenticationToken`(아직 인증되지 않은 상태의 객체) 생성.
3. `AuthenticationFilter`가 `AuthenticationManager`에게 인증 요청.
4. `AuthenticationManager`가 `AuthenticationProvider` 중 현재 인증 방식을 처리할 수 있는 `Provider`에게 위임.
5. `AuthenticationProvider`가 `UserDetailsService` 호출 (→ DB에서 사용자 정보 조회).
6. `UserDetailsService`가 `UserDetails` 반환 (특정 프로젝트의 User 엔티티를 모르기 때문에 공통 규격인 `UserDetails` 사용함).
7. `UserDetailsService`가 사용자 정보를 `Provider`에게 반환.
8. 인증 성공 결과를 `AuthenticationManager`에게 반환.
9. `AuthenticationManager`가 인증 결과를 `Filter`에게 반환.
10. `AuthenticationFilter`가 인증된 사용자 정보를 `SecurityContextHolder`에 저장.
* **[결과]**: 이후 Controller/Service에서 현재 로그인한 사용자가 누구인지 확인 및 사용 가능함.

## 3. CSRF & CORS
* **CSRF (Cross-Site Request Forgery)**
    * 사용자의 의도와 무관하게 위조된 요청을 전송하는 공격.
    * JWT 기반 인증에서는 인증 정보를 쿠키가 아닌 `Authorization` 헤더에 담아 전송하므로 CSRF 보호 설정을 비활성화(`.csrf(AbstractHttpConfigurer::disable)`)함[cite: 337, 338].
* **CORS (Cross-Origin Resource Sharing)**
    * 다른 출처(프로토콜, 도메인, 포트가 다른 경우)의 리소스 요청 허용 여부를 결정하는 브라우저 정책.
    * `CorsConfig`를 통해 허용할 출처, HTTP 메서드, 요청 및 응답 헤더 등을 설정해야 함.

## 4. JWT (JSON Web Token)
* 서버가 사용자 정보를 담아 발급하는 서명된 토큰.
* 세션 방식(서버에 상태 저장)과 달리 서버 부하를 줄일 수 있는 Stateless 방식임.

### JWT의 구조
* **Header**: 암호화 알고리즘 및 토큰 타입 정보.
* **Payload**: 전달하려는 정보(Claim - 사용자 ID, 권한, 만료시간 등).
* **Signature**: Header와 Payload를 기반으로 Secret Key를 사용해 생성한 서명으로, 토큰의 조작 여부 검증.

### Access Token vs Refresh Token
**JWT를 하나만 사용할 경우의 문제점**
* 만료 시간이 길면: 토큰이 탈취됐을 때 공격자가 오랫동안 사용할 수 있음.
* 만료 시간이 짧으면: 토큰이 자주 만료되어 계속 다시 로그인해야 함.

위 문제를 해결하기 위해 용도에 따라 두 가지로 나누어 사용함.
* **Access Token**: API에 접근하기 위한 출입증 역할을 하며, 보안을 위해 만료 시간을 짧게 설정함.
* **Refresh Token**: Access Token이 만료되었을 때 이를 재발급받기 위해 사용하는 토큰임.

### JWT 동작 원리
1. 클라이언트가 ID와 Password를 전달하여 로그인 요청을 보냄.
2. 서버는 ID와 Password가 맞는지 확인하고, Access Token과 Refresh Token을 생성함.
3. 서버가 생성된 토큰들을 클라이언트에게 전달함.
4. 클라이언트는 전달받은 토큰을 `localStorage` 등에 저장함.
5. 이후 API 요청 시 클라이언트는 헤더에 Access Token을 담아서 요청함.
6. 서버는 헤더의 Access Token을 검증하고, 유효하다면 요청을 처리함.
7. 처리 결과를 클라이언트에게 응답(Response)함.

### Token 설계 시 보안 고려사항
* **Access Token 만료 시간**: 짧게 설정하기.
* **Refresh Token 관리**: 서버에서 관리하기.
* **로그아웃 처리**: 로그아웃 시 Refresh Token 삭제하기.
* **정보 최소화**: 토큰에는 민감한 정보를 넣지 않기.
* **Secret Key 관리**: 환경변수 등을 통해 안전하게 관리하기.
* **통신 보안**: HTTPS 사용하기.

## 6. Access Token과 Refresh Token의 역할
* **Access Token**
    * 클라이언트가 서버의 보호된 리소스(API)에 접근할 때 사용하는 실질적인 출입증임.
    * 토큰이 탈취될 경우를 대비해 보안상 유효 기간(만료 시간)을 매우 짧게 설정함 (예: 30분).
    * API 요청 시 HTTP 헤더(`Authorization`)에 담아 전송하며, 서버는 이를 검증하여 접근 권한을 확인함.

* **Refresh Token**
    * 수명이 짧은 Access Token이 만료되었을 때, 사용자가 매번 다시 로그인하지 않고도 새로운 Access Token을 발급받을 수 있도록 해주는 토큰임.
    * 유효 기간을 상대적으로 길게 설정함 (예: 1주 ~ 2주).
    * 탈취 위험을 줄이고 통제권을 쥐기 위해 서버 측의 데이터베이스(RDBMS)나 인메모리 저장소(Redis)에 저장하여 관리하는 것이 일반적임.

## 7. Spring Boot에서의 Refresh Token 구현 및 동작 흐름
**1. 토큰 생성 및 발급 (로그인 성공 시)**
* 사용자가 이메일과 비밀번호로 로그인에 성공하면, 서버는 Access Token과 Refresh Token을 동시에 생성함.
* 이때 서버는 생성된 Refresh Token을 사용자 식별자(ID)와 매핑하여 DB 또는 Redis에 저장함.
* 클라이언트에게 두 토큰을 모두 전달함 (주로 Access Token은 JSON 응답 바디에, Refresh Token은 보안을 위해 HttpOnly 쿠키에 담아 반환함).

**2. Access Token 만료 및 재발급 요청**
* 클라이언트가 서비스 이용 중 Access Token이 만료되어 API 호출 시 인증 에러(`401 Unauthorized` 또는 `ExpiredJwtException`)가 발생함.
* 에러를 응답받은 클라이언트는 보관 중이던 Refresh Token을 서버의 재발급 API(예: `POST /api/auth/refresh`)로 전송하여 새로운 토큰을 요청함.

**3. Refresh Token 검증 및 새로운 토큰 발급**
* 서버는 전달받은 Refresh Token 자체의 유효성(서명, 만료 여부)을 먼저 검증함.
* 검증에 통과하면, DB(또는 Redis)에 저장된 해당 사용자의 Refresh Token 값과 일치하는지 확인하여 유효한 요청인지 2차로 확인함.
* 최종적으로 검증이 완료되면 새로운 Access Token을 생성하여 클라이언트에게 반환함.
* (보안을 한층 강화하기 위해 새로운 Access Token과 함께 새로운 Refresh Token을 매번 다시 발급하는 RTR(Refresh Token Rotation) 방식을 사용하기도 함).

**4. 로그아웃 처리 시 토큰 폐기**
* 사용자가 로그아웃을 요청할 경우, 서버(DB 또는 Redis)에 저장되어 있던 해당 사용자의 Refresh Token을 강제로 삭제함.
* 이를 통해 만약 토큰이 탈취되었더라도 더 이상 새로운 Access Token을 발급받을 수 없도록 원천 차단함.