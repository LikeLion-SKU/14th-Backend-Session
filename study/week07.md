## 인증 vs 인가
- 인증 (Authentication)
  - 사용자의 신원을 확인하는 행위
  - 실패 -> Unauthorized (401 에러)
- 인가 (Authorization)
  - 인증된 사용자에게 접근 권한을 부여 하는것
  - 실패 -> Forbidden(403 에러)

## Spring Security
- Spring에서 인증과 인가를 처리해주는 보안 프레임워크
- Controller에 도달하기 전에 Spring Security가 보안 검사를 진행

### Spring Security 인증 처리 과정
1. 클라이언트 로그인 요청
2. AuthenticationFilter가 **UsernamePasswordAuthenticationToken (아직 인증되지 않은 상태의 객체)** 생성
3. AuthenticationFilter가 AuthenticationManager에게 인증 요청
4. AuthenticationManager가 AuthenticationProvider 중 현재 인증 방식을 처리할 수 있는 Provider에게 위임
5. AuthenticationProvider가 UserDetailsService 호출 (DB에서 사용자 정보 조회)
6. UserDetailsService가 UserDetails 반환
7. UserDetailsService가 사용자 정보를 Provider에게 반환
8. 인증 성공 결과를 AuthenticationManager에게 반환
9. AuthenticationManager가 인증 결과를 Filter에게 반환
10. AuthenticationFilter가 인증된 사용자 정보를 SecurityContextHolder에 저장

## SecurityConfig.java 파일
- **requestMachers()**
  - 특정 요청과 일치하는 URL에 대한 접근 설정
- **.permitAll()**
  - 인증/인가 없이 누구나 접근 가능하다는 것
- **.anyRequest()**
  - permitAll 이외의 URL에 대한 요청 설정
- **.authenticated()**
  - 인증 필요

## CSRF 와 CORS
- CSRF
  - 사용자가 원하지 않은 요청이 대신 전송되는 공격을 방어
  - 인증 정보를 쿠키가 아닌 Authorization 헤더에 담아서 전송하기 때문에 CSRF 비활성화
- CORS
  - 다른 출처가 API를 호출할 수 있는지 정하는 정책

## JWT
- 서버가 사용자 정보를 담아서 발급하는 서명된 토큰
- 흐름
  1. 로그인 성공
  2. 서버가 JWT 발급
  3. 클라이언트 JWT 저장
  4. 이후 요청할때마다 JWT를 함께 전송
  5. 서버가 JWT를 검증하고 사용자 식별
- 구조
  - Header.Payload.Signature 3 부분으로 구성
  - 각 부분은 .으로 구분

### JWT 구조
- **Header**
  - 어떠한 알고리즘으로 암호화 할 것인지, 어떠한 토큰을 사용할 것인지에 대한 정보
- **Payload**
  - claim이라고 부르는 전달하려는 정보
  - 민감한 정보 포함하지 않음
- **Signature**
  - Header와 Payload를 기반으로 Secret Key를 사용해 서명을 생성하고 토큰을 받을 때 다시 서명을 검증

## Access Token 과 Refresh Token
- Access Token
  - API에 접근하기 위한 출입증
- Refresh Token
  - Access Token을 다시 발급받기 위한 토큰

### JWT 동작 원리
1. 로그인 요청 
2. 로그인 성공하면 Access Token, Refresh Token 생성
3. 클라이언트한테 Access Token, Refresh Token 전달
4. 클라이언트는 사용자의 localStorage에 저장
5. 헤더에 Access Token 담아서 서버한테 request
6. 서버는 Access Token 검증
7. 클라이언트한테 다시 반환

### Token 설계 시 보안 고려사항
- Secret Key는 안전하게 관리 (환경변수로 관리)
- HTTPS 사용하기
- Access Token의 만료 시간은 짧게 설정
- Refresh Token은 서버에서 관리
- 로그아웃 시 Refresh Token 삭제
- 토큰에는 민감한 정보 넣지 않기

## 과제 1
- Refresh Token의 역할
  - Access Token은 만료 시간을 10~15분 정도로 설정하지만 Refresh Token은 만료 시간을 12~24시간으로 설정
  - 로그인을 하고 발급된 Access Token이 만료가 되면 Refresh Token을 이용해 Access Token을 다시 발급 받음
  - Refresh Token은 서버 DB에 저장
- Access Token의 역할 
  - 실제로 API를 호출할 때 사용하는 인증 토큰
- Refresh Token의 흐름
  1. 로그인을 성공하면 서버가 Access Token + Refresh Token 둘 다 발급
  2. API 요청 → Access Token을 헤더에 담아서 요청 
  3. Access Token 만료 → 서버가 401 에러 반환 
  4. 자동 재발급 → 클라이언트가 Refresh Token으로 /auth/refresh 요청 
  5. 새 Access Token 발급 → 사용자는 로그아웃 없이 계속 사용 
  6. Refresh Token도 만료 → 다시 로그인 필요
- Refresh Token Rotation
  - Refresh Token을 쓸 때마다 새 Refresh Token도 같이 발급하고 기존 건 폐기하는 전략
  - 탈취를 감지하고 모든 토큰을 강제 무효화 할 수 있음


