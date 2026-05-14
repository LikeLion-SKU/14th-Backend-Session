# SKU LIKELION - WEEK 7
## Spring Security와 JWT

---

## 목차

1. 인증과 인가
2. Spring Security
3. CSRF와 CORS
4. JWT와 Access/Refresh Token
5. 로그인/회원가입 API 구조

---

## 1. 인증과 인가

### 인증이 왜 필요할까?

> "요청을 보낸 클라이언트가 누구지?"에 대한 답변

- **비로그인 사용자** → 마이페이지 내 정보 조회 API (`/api/users/me`) 접근 불가
- **일반 사용자** → 관리자 전용 부스 수정 API (`/api/admin/booths`) 접근 불가

---

### 인증 (Authentication)

> "너 누구야?" → **신원 확인**

- 사용자의 신원이 진짜인지 확인하는 행위
- 실패 시 **401 Unauthorized** (비로그인)

| 상황 | 결과 |
|------|------|
| 상황 1: 로그인 성공 | 인증 완료 |
| 상황 2: 마이페이지 접근 | 인증된 사용자만 가능 |
| 상황 3: 관리자 페이지 접근 | 인증된 사용자 + 관리자 권한 보유 |

---

### 인가 (Authorization)

> "너 이거 할 권한있어?" → **권한 검증**

- 인증된 사용자(선행 조건)에게 접근 권한을 부여
- 실패 시 **403 Forbidden** (권한 부족)

---

## 2. Spring Security

### Spring Security란?

> Spring에서 인증과 인가를 처리해주는 **보안 프레임워크**

- 모든 API마다 직접 로그인 여부/권한 확인 코드를 작성하지 않아도 됨
- Controller에 도달하기 **전에 가로채서** 보안 검사를 수행
- 인증 실패(비로그인) → **401** / 인가 실패(권한 없음) → **403**

---

### Spring Security Architecture

| 컴포넌트 | 역할 |
|----------|------|
| **SecurityFilterChain** | 보안 관련 필터들의 연속체 (CORS, 로그인, JWT 검증, 권한 확인 등) |
| **ProviderManager** | 현재 인증 방식(자체로그인/OAuth 등)을 처리할 수 있는 Provider를 찾아 위임 |
| **UserDetailsService** | 사용자 세부 정보 불러오기 → UserDetails 형태로 반환 |
| **PasswordEncoder** | 회원가입: 비밀번호 암호화 저장 / 로그인: 입력 비밀번호 ↔ DB 암호화 비밀번호 비교 |
| **SecurityContextHolder** | 인증 성공 시 사용자 정보 저장 → 이후 보안 검사에 사용 |

**Authentication 인증 객체 구성:**
- `Principal` : 인증된 사용자
- `Credentials` : 비밀번호
- `Authorities` : 부여된 권한

---

### Spring Security 인증 처리 과정 (10단계)

1. **클라이언트 로그인 요청**
2. `AuthenticationFilter`가 `UsernamePasswordAuthenticationToken` 생성 (아직 인증되지 않은 상태의 객체)
3. `AuthenticationFilter`가 `AuthenticationManager`에게 인증 요청
    - Filter >> "아이디/비밀번호 가져왔으니 인증 좀 해줘."
4. `AuthenticationManager`가 `AuthenticationProvider` 중 현재 인증 방식을 처리할 수 있는 Provider에게 위임
5. `AuthenticationProvider`가 `UserDetailsService` 호출 → DB에서 사용자 정보 조회
6. `UserDetailsService`가 `UserDetails` 반환 (특정 프로젝트의 User 엔티티를 모르기 때문에 공통 규격인 UserDetails 사용)
7. `UserDetailsService`가 사용자 정보를 Provider에게 반환 (요청으로 들어온 비밀번호 == DB에 저장된 비밀번호 검증 via PasswordEncoder)
8. 인증 성공 결과를 `AuthenticationManager`에게 반환
9. `AuthenticationManager`가 인증 결과를 Filter에게 반환
    - Manager >> "이 사용자는 인증 성공했고, 권한은 ROLE_USER이야."
10. `AuthenticationFilter`가 인증된 사용자 정보를 `SecurityContextHolder`에 저장

**[결과]** 이후 Controller/Service에서 현재 로그인한 사용자가 누구인지 확인·사용 가능

---

### SecurityConfig 주요 설정 개념

| 메서드 | 설명 |
|--------|------|
| `requestMatchers()` | 특정 요청과 일치하는 URL에 대한 접근 설정 |
| `permitAll()` | 인증/인가 없이 누구나 접근 가능 |
| `anyRequest()` | permitAll 이외의 URL에 대한 요청 설정 |
| `authenticated()` | 인증 필요 (인가 필요 X) |

---

## 3. CSRF & CORS

### CSRF (Cross-Site Request Forgery) - 사이트간 요청 위조

- **쿠키 기반 인증**: 브라우저에서 요청 시 자동으로 쿠키 함께 전송
  → 로그인된 사용자의 브라우저가 사용자 몰래 요청을 전송하는 공격 가능
- **JWT 기반 인증**: 인증 정보를 **쿠키가 아닌 Authorization 헤더**에 담아 전송
  → **CSRF 비활성화 가능!**

---

### CORS (Cross-origin Resource Sharing) - 다른 출처 요청 허용 여부를 정하는 브라우저 정책

> **Origin = 프로토콜 + 도메인 + 포트**로 결정

| 예시 | 설명 |
|------|------|
| 프론트: `http://localhost:3000` / 백엔드: `http://localhost:8080` | 포트가 달라 다른 Origin |
| 프론트: `https://skuhomepage.site` / 백엔드: `https://api.skuhomepage.site` | 도메인이 달라 다른 Origin |

→ 허용할 Origin 설정 필요!

---

## 4. JWT와 Access/Refresh Token

### 세션 기반 인증 vs 토큰 기반 인증

> 로그인에 성공한 사용자를 다음 요청에서 알아보는 방법

**HTTP의 Stateless 특성** → 사용자를 식별하기 위한 별도 방법 필요

| 방식 | 통신 매개체 | 인증 확인 방법 |
|------|-------------|----------------|
| 세션 (Session) | 요청 + 세션 ID | 세션 저장소에서 세션 ID를 조회해 인증 상태 확인 |
| JWT (Token) | 요청 + Access Token | Access Token의 서명·만료 시간을 검증해 인증 상태 확인 |

**JWT 장점:** 서버 부하 감소, 서버·플랫폼 확장성  
**JWT 단점:** 사용자를 기억(state)하지 못함

---

### JWT (JSON Web Token)

> 서버가 사용자 정보를 담아서 발급하는 **서명된 토큰**

#### JWT Flow

1. 로그인 성공
2. 서버가 JWT 발급
3. 클라이언트 JWT 저장
4. 이후 요청마다 JWT를 함께 전송 (`Authorization: Bearer accessToken`)
5. 서버가 JWT를 검증하고 사용자 식별 (서버에서 만든 토큰인지, 만료되지 않았는지, 어떤 사용자의 토큰인지 등)

---

### JWT의 구조

`Header.Payload.Signature` (각 부분은 `.`으로 구분)

#### Header
어떠한 알고리즘으로 암호화할 것인지(alg), 어떠한 토큰을 사용할 것인지(typ)에 대한 정보

#### Payload
- `claim`이라고 부르는 전달하려는 정보 (사용자 ID, 이메일, 권한, 만료시간 등)
- 인코딩된 내용으로 **민감한 정보 포함 X**

#### Signature
- Header와 Payload를 기반으로 Secret Key를 사용해 서명 생성
- 토큰을 받을 때 다시 서명을 검증
- Payload가 조작되면 Signature와 불일치 → **위변조 탐지 가능**

---

### JWT 하나만 사용하면 안 되는 이유

> JWT를 하나만 사용하면 편의성과 보안 사이에서 문제가 발생

- **만료 시간이 길면** → 토큰이 탈취됐을 때 공격자가 오랫동안 사용 가능
- **만료 시간이 짧으면** → 토큰이 자주 만료되어 계속 다시 로그인해야 함

→ **Access Token + Refresh Token** 두 가지를 함께 사용!

---

### Access Token vs Refresh Token

#### Access Token
- API에 접근하기 위한 **출입증**
- 요청 헤더에 포함: `Authorization: Bearer accessToken`
- **만료 시간을 짧게** 설정 (보안)

#### Refresh Token
- **Access Token을 다시 발급받기 위한 토큰**
- Access Token이 만료됐을 때 사용
- `POST /api/auth/refresh` 엔드포인트로 재발급 요청

---

### JWT 동작 원리

```
Client (Web Browser)                              Server
      |                                               |
      |--1. 로그인 요청 (ID, Password 전달)--------->|
      |                                    2. Access token, Refresh token 생성
      |<---3. Access token, Refresh token 전달--------|
  4. localStorage에 저장
      |                                               |
      |--5. 헤더에 Access token 담아서 Request------->|
      |                               6. Access token 검증 (유효하면 Response)
      |<---7. Response---------------------------------|
```

---

### Token 설계 시 보안 고려사항

| 고려사항 | 내용 |
|----------|------|
| Access Token 만료 시간 | 짧게 설정하기 |
| Refresh Token 관리 | 서버에서 관리하기 (실무: Redis 또는 별도 테이블) |
| 로그아웃 처리 | Refresh Token 삭제하기 |
| 토큰 내용 | 민감한 정보 넣지 않기 |
| Secret Key | 환경변수로 안전하게 관리하기 |
| 통신 | HTTPS 사용하기 |

---

## 5. 회원가입 & 로그인 API 구조

### 주요 컴포넌트

- **SecurityFilterChain**: API별 접근 권한 설정 (JWT Stateless 방식)
- **PasswordEncoder (BCrypt)**: 비밀번호 암호화 저장 및 검증
- **AuthenticationManager**: 인증 처리 위임
- **CustomUserDetails**: User 엔티티를 Spring Security에서 사용할 수 있도록 래핑
- **CustomUserDetailsService**: email 또는 userId로 사용자 조회
- **JwtProvider**: Access Token 생성, 유효성 검증, userId 추출
- **JwtAuthenticationFilter**: 요청마다 Authorization 헤더에서 JWT를 추출하여 인증 처리

### 왜 auth를 따로 구분하나요?

사용자 정보 관리와 인증 처리는 책임이 다르기 때문.
- 회원가입: 새로운 사용자를 **생성**
- 로그인: 이미 존재하는 사용자가 맞는지 **확인** / AccessToken을 **발급**

---



### Refresh Token이란?

- Access Token이 **만료되었을 때** 새로운 Access Token을 재발급받기 위한 토큰
- Access Token보다 **유효기간이 길게** 설정 (보통 2주~1달)
- **서버에 저장**하여 관리 (실무: Redis 또는 별도 DB 테이블)

### Access Token vs Refresh Token 비교

| 구분 | Access Token | Refresh Token |
|------|-------------|---------------|
| 역할 | API 접근 인증 출입증 | Access Token 재발급용 |
| 만료 시간 | 짧게 (30분 ~ 1시간) | 길게 (2주 ~ 1달) |
| 전송 위치 | Authorization 헤더 | 요청 Body 또는 Cookie |
| 저장 위치 | 클라이언트 (localStorage 등) | 서버 (Redis / DB) |

### Refresh Token 동작 흐름

1. 로그인 성공 → Access Token + Refresh Token 발급
2. 서버는 Refresh Token을 DB/Redis에 저장
3. 클라이언트는 두 토큰 모두 저장
4. API 요청 시 Access Token 사용
5. Access Token 만료 시 → Refresh Token으로 재발급 요청
6. 서버는 Refresh Token 검증 → 유효하면 새 Access Token 발급
7. 로그아웃 시 → 서버에서 Refresh Token 삭제
