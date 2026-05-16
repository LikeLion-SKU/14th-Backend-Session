## 🫠 인증 인가

---

왜 인증이 필요할까? → 누가 요청을 보냈는지 확인하고

인증이 필요한 이유 : 누가 요청을 보냈는지 확인하기 위함

(영어로 단어 알아두는것 추천! )

- 인증(Authentication) : 신원 확인 → 401 비로그인
- 인가(Authorization)  : 권한 검증 → 403 권한 부족

- Spring Security이란 : 인증인가를 처리해주는 보안 프레임워크
- 작동 방식

    ```kotlin
    API 요청 → Spring Security(보안검사) → 컨트롤러 요청
    ```

## 🫠 스프링 시큐리티

---

- 의존성 추가

```kotlin
// Spring Security
implementation 'org.springframework.boot:spring-boot-starter-security'

```

1. CSRF FORS

```kotlin
http.csrf(csrf -> csrf.disable())
.cors(cors -> cors.configurationSource(corsConfigurationSource()))
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/auth/login").permitAll()
    .anyRequest().authenticated()
)
);
```

- csrf : 사용자가 원하지 않은 요청이 대신 전송되는 공격 방어
- cors : 다른 출처가 API를 호출할 수 있는지 확인하는 정책

- 세션 기반 인증 : 요청 + 세션 ID
- 토큰 기반 인증 : 요청 + access token

## 🫠 JWT와 Access/Refresh Token

---

- JWT : Json Web Token

```kotlin
로그인 성공 -> JWT 발급 -> 클라이언트가 JWT 저장 -> 이후 요청마다 JWT를 함께 전송(Authorization : Bearer access Token)
-> 서버가 JWT를 검증하고 사용자 식별 
```

- JWT 구조 :
    - 헤더 : 어떤 알고리즘으로 암호화, 어떤 토큰 사용
    - 페이로드 : claim이라 부르는 전달하려는 정보(민감 정보X)
    - 시그니처 : 시크릿키를 사용해 서명 생성
- 토큰 설계시 보안 고려 사항
    - Acess token 만료시간은 짧게 (개발할 땐 길게!)
    - Refresh Token은 서버에서 관리하기
    - 로그아웃 시 Refresh Token 삭제하기
    - secret key는 안전하게 관리하기

## 🫠 로그인/회원가입 API 구현 실습

---

- 회원가입
- ![image (23).png](img%2Fimage%20%2823%29.png)
- ![image (24).png](img%2Fimage%20%2824%29.png)


- 로그인 API
- ![image (25).png](img%2Fimage%20%2825%29.png)
- ![image (26).png](img%2Fimage%20%2826%29.png)

## 🫠 과제) SpringBoot에서 RefreshToken 구현

---

다음과 같은 흐름으로 진행된다

```kotlin
로그인 요청 -> 토큰발급 -> API 요청 ->
(Access토큰 만료시) 클라이언트 : Refresh 토큰 전송 & 서버 : 새 Acces토큰 발급 ->
(Refresh 토큰 만료시) 클라이언트 재로그인 필요 
```

Access Token

- 역할: 실제 API 요청 인증에 사용되는 토큰
- 유효기간: 짧음 (보통 15분 ~ 1시간)
- 저장 위치: 메모리 또는 클라이언트 상태
- 특징: 매 요청마다 Authorization 헤더에 포함해서 전송

Refresh Token

- 역할: Access Token이 만료됐을 때 새 Access Token을 발급받기 위한 토큰
- 유효기간: 긺 (보통 7일 ~ 30일)
- 저장 위치: HttpOnly 쿠키 또는 서버 DB
- 특징: Access Token 재발급 요청 시에만 사용, 일반 API 요청엔 포함 안 함

리프레시 토큰은 다음과 같이 리프레시 토큰을 발급받아오고 인증한다

```kotlin
@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshToken createRefreshToken(String username)
    {
        // 기존 토큰이 있으면 삭제 (한 유저 = 하나의 Refresh Token)
        refreshTokenRepository.deleteByUsername(username);

        RefreshToken refreshToken = new RefreshToken(
            username,
            jwtUtil.generateRefreshToken(username),
            LocalDateTime.now().plusDays(7)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token)
    {
        RefreshToken refreshToken = refreshTokenRepository . findByToken (token)
            .orElseThrow(() -> new RuntimeException("Refresh Token을 찾을 수 없습니다."));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException ("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
        }

        return refreshToken;
    }

    public void deleteByUsername(String username)
    {
        refreshTokenRepository.deleteByUsername(username);
    }
}
```
