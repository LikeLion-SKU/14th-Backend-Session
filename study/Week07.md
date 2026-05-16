## 7주차 세션 요약: Spring Security와 JWT

### 1. 인증(Authentication)과 인가(Authorization)
* 인증은 요청을 보낸 클라이언트가 누구인지 신원을 확인하는 절차입니다.
* 인증 실패 시에는 401 Unauthorized 에러가 발생하며 주로 비로그인 상태에서 나타납니다.
* 인가는 인증된 사용자에게 특정 리소스에 접근할 권한이 있는지 검증하는 단계입니다.
* 권한이 부족하여 인가에 실패할 경우 403 Forbidden 에러가 반환됩니다.

### 2. Spring Security 주요 아키텍처
* Spring Security는 애플리케이션의 인증과 인가를 전담하는 보안 프레임워크입니다.
* SecurityFilterChain은 여러 보안 필터가 사슬처럼 연결된 구조로, 요청이 컨트롤러에 도달하기 전 보안 검사를 가로채어 수행합니다.
* UserDetailsService는 데이터베이스에서 사용자 정보를 불러와 보안 체계에 맞는 객체로 변환해주는 역할을 합니다.
* PasswordEncoder는 회원가입 시 비밀번호를 안전하게 암호화하고, 로그인 시 입력값과 비교 검증할 때 사용됩니다.

### 3. CSRF 및 CORS 설정
* CSRF(사이트 간 요청 위조)는 사용자의 의도와 상관없이 공격자가 의도한 요청을 서버로 전송하는 공격입니다. JWT 기반의 무상태(Stateless) API 서버에서는 보통 이 설정을 비활성화합니다.
* CORS(교차 출처 리소스 공유)는 다른 도메인이나 포트에서 API를 호출할 수 있는지 결정하는 브라우저 정책입니다.
* 프론트엔드와 백엔드의 포트나 도메인이 다를 경우, 서버 측에서 허용할 출처(Origin)를 반드시 명시해야 합니다.

### 4. JWT(JSON Web Token) 기반 인증
* JWT는 클라이언트 식별을 위해 서버가 발급하는 서명된 토큰으로, 무상태 특성을 가진 통신 환경에서 인증 상태를 유지하기 위해 사용됩니다.
* 토큰 구조는 헤더(알고리즘), 페이로드(사용자 데이터), 시그니처(위조 방지 서명)의 세 부분으로 나뉩니다.
* Access Token은 실제 서비스 접근에 사용되는 출입증이며 보안을 위해 유효 기간을 짧게 설정합니다.
* Refresh Token은 Access Token이 만료되었을 때 새로운 토큰을 다시 발급받기 위한 용도로 활용됩니다.

### 5. Access Token과 Refresh Token의 역할 비교
* Access Token은 사용자가 보호된 리소스에 접근할 때 인증 수단으로 사용되며, 탈취 위험을 줄이기 위해 만료 시간을 짧게(보통 15분~30분) 설정합니다.
* Refresh Token은 Access Token이 만료된 후 사용자가 재로그인 없이 새로운 Access Token을 발급받을 수 있도록 하는 장기 토큰입니다.
* Refresh Token은 일반적으로 유효 기간을 길게(7일~30일) 설정하며, 서버 측 데이터베이스나 Redis에 저장하여 관리합니다.
* Access Token은 클라이언트의 Authorization 헤더에 담아 전송하고, Refresh Token은 HttpOnly 쿠키에 저장하여 XSS 공격으로부터 보호하는 것이 권장됩니다.

### 6. Spring Boot에서 Refresh Token 구현 흐름
* 사용자가 로그인에 성공하면 서버는 Access Token과 Refresh Token을 함께 생성하여 클라이언트에 전달합니다.
* 서버는 발급한 Refresh Token을 데이터베이스 또는 Redis에 사용자 정보와 함께 저장합니다.
* 클라이언트는 이후 API 요청 시 Access Token을 Authorization 헤더에 Bearer 형식으로 포함하여 전송합니다.
* Access Token이 만료되면 서버는 401 Unauthorized 응답을 반환하고, 클라이언트는 Refresh Token을 이용해 토큰 재발급 엔드포인트(/api/auth/reissue 등)에 요청합니다.
* 서버는 전달받은 Refresh Token의 유효성을 검증하고, 저장소에 보관된 값과 일치하는지 확인한 뒤 새로운 Access Token을 발급합니다.
* 보안 강화를 위해 Refresh Token Rotation 방식을 적용할 수 있으며, 이 경우 재발급 시 Refresh Token도 함께 갱신하고 기존 토큰은 폐기합니다.

### 7. Refresh Token 저장 및 관리 전략
* 서버 측 저장소로는 관계형 데이터베이스(MySQL 등)에 별도의 refresh_token 테이블을 두거나, Redis와 같은 인메모리 저장소에 TTL을 설정하여 관리하는 방법이 있습니다.
* Redis를 사용하면 만료 시간이 지난 토큰이 자동으로 삭제되어 별도의 정리 로직이 필요 없고, 조회 성능이 우수합니다.
* 데이터베이스 방식은 토큰 발급 이력 추적이 가능하다는 장점이 있으나, 만료된 토큰을 주기적으로 정리하는 스케줄링 작업이 필요합니다.
* 로그아웃 시에는 서버 저장소에서 해당 사용자의 Refresh Token을 삭제하여 더 이상 토큰 재발급이 불가능하도록 처리합니다.

### 8. Refresh Token 보안 고려사항
* Refresh Token이 탈취될 경우 장기간 악용될 수 있으므로, 반드시 서버 측 검증 로직을 두어야 합니다.
* Refresh Token Rotation을 적용하면 한 번 사용된 토큰은 즉시 무효화되므로, 탈취된 토큰의 재사용을 탐지하고 차단할 수 있습니다.
* 이미 사용된 Refresh Token으로 재발급 요청이 들어오면, 해당 사용자의 모든 Refresh Token을 폐기하여 세션 전체를 무효화하는 방어 전략을 적용할 수 있습니다.
* 클라이언트 측에서 Refresh Token을 저장할 때는 HttpOnly, Secure, SameSite 속성이 설정된 쿠키를 사용하여 스크립트 접근 및 CSRF 공격을 방지해야 합니다.
