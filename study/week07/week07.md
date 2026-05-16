## 1. 인증과 인가 (Authentication & Authorization)
1. 인증 (Authentication)
- 사용자의 신원이 진짜인지 확인하는 행위
- 실패 시 401 Unauthorized 에러가 발생

2. 인가 (Authorization)
- 인증된 사용자에게 특정 자원에 대한 접근 권한을 부여하는 행위
- 실패 시 403 Forbidden 에러가 발생

## 2. Spring Security
- Spring에서 인증과 인가를 처리해주는 보안 프레임워크
- DispatcherServlet을 지나 Controller에게 요청이 가기 전, Spring Security Filter에서 해당 요청을 가로채 보안 검사를 수행한다.

### [Spring Security 인증 처리 과정]
1. 클라이언트 로그인 요청
2. AuthenticationFilter가 UsernamePasswordAuthenticationToken 생성 (아직 인증되지 않은 상태의 객체)
3. AuthenticationFilter가 AuthenticationManager에게 인증 요청
4. AuthenticationManager가 AuthenticationProvider 중 현재 인증 방식을 처리할 수 있는 Provider에게 작업 위임
5. AuthenticationProvider는 UserDetailsService를 호출하여 DB에서 사용자 정보(UserDetails) 조회
6. UserDetailsService가 UserDetails 반환 (특정 프로젝트의 User Entity를 미리 알 수 없으니, 공통 규격인 UserDetails를 사용한다.)
7. UserDetailsService가 사용자 정보를 Provider에게 반환 (이때, 요청으로 들어온 비밀번호와 DB에 저장된 비밀번호를 검증한다. PasswordEncoder를 사용한다.)
8. 인증 성공 결과를 AuthenticationManager에게 반환한다.
9. AuthenticationManager가 인증 결과를 Filter에게 반환한다. (해당 사용자는 인증 성공했으며, 권한은 ROLE_USER임을 알려준다.)
10. AuthenticationFilter가 인증된 사용자 정보를 SecurityContextHolder에 저장한다.
-> 이후 Controller/Service에서 현재 로그인한 사용자가 누구인지 확인 & 사용한다.

## 3. CSRF & CORS
SecurityFilterChain을 설정할 때 마주치는 설정들
1. CSRF(Cross-Site Request Forgery)
- 사이트간 요청 위조
- 사용자가 원하지 않은 요청이 대신 전송되는 공격
- 쿠기 기반 인증 방식은 브라우저에서 요청을 보낼 때 자동으로 브라우저가 갖고 있는 쿠키를 함께 전송한다.
- 이때 사용자가 공격 코드가 있는 게시물 같은 요소를 클릭한다면, 해당 요청은 사용자의 브라우저가 갖고 있는 쿠키를 담고 요청이 전송되게 된다.
- 서버는 사용자의 브라우저가 갖고 있는 쿠키를 신뢰하므로, 해당 사용자의 쿠키를 이용해서 공격 스크립트가 실행되게 된다.
- JWT는 위처럼 쿠키가 아니라 Authorization 헤더에 인증 정보를 담아 전송하므로, csrf를 비활성화 하는 것이다.

2. CORS (Cross-origin Resource Sharing)
- 다른 출처 요청 허용 여부를 정하는 브라우저 정책
- Origin = 프로토콜 + 도메인 + 포트
- 프론트와 백엔드의 Origin이 다른 경우, 백엔드에서는 요청 Origin이 다르므로 Access-Control-Allow-Origin 헤더를 응답으로 주지 않는다.
- 따라서 브라우저는 해당 헤더가 없기 때문에 서버로부터 응답을 받을 수 없다 (이는 Simple | Preflight 방식에 따라 다르다 https://kjm99d.tistory.com/30)
- 따라서, 프론트가 백엔드 api를 사용할 수 있도록 cors 설정을 허용해주어야한다.

## 4. 세션 기반 인증 vs 토큰 기반 인증
- 로그인에 성공한 사용자를 다음 요청에서 알아보는 방법

1. HTTP의 Stateless 특성
- 장점: 서버 부하 감소, 서버 & 플랫폼 확장성
- 단점: 사용자를 기억(state)하지 못함

2. 사용자를 식별하기 위한 방법
- 세션 방식: 요청 + 세션 Id
- JWT 방식: 요청 + Access Token

## 5. JWT(Json Web Token)
- 서버가 사용자 정보를 담아서 발급하는 서명된 토큰

1. JWT 구조
- Header: 어떤 알고리즘으로 암호화 할 것인지, 어떤 토큰을 사용할 것 인지에 대한 정보
- Payload: claim이라고 부르는 전달하려는 정보 (사용자 ID, 이메일, 권한, 만료시간 등). 인코딩 된 내용으로, 민감한 정보를 포함하면 안된다.
- Signature: Heder와 Payload를 기반으로 Secret Key를 사용해 서명 생성. 토큰을 받을 때 다시 서명을 검증한다.

2. Access | Refresh token
- Access Token: API에 접근하기 위한 출입증
- Refresh Token: Access Token을 다시 발급받기 위한 토큰

3. Token 설계 시 보안 고려사항
- Access Token 만료 시간 짧게 설정
- Refresh Token은 서버에서 관리
- 로그아웃 시 Refresh Token 삭제하기
- 토큰에는 민감한 정보 넣지 않기
- Secret Key는 안전하게 관리하기
- HTTPS 사용하기