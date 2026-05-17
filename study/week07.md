## RefreshToken VS AccessToken


- 엑세스 토큰: 인증이 필요한 api 자원에 접근 할 수 있도록 권한을 부여함.

- 리프레쉬 토큰: Access Token이 만료되었을 때 새로운 Access Token을 발급받기 위한 인증 수단

#### 엑세스 토큰은 매 요청마다 네트워크를 통해 오고 가기 때문에 탈취 위험이 상대적으로 높음.
-> 엑세스 토큰의 경우 악의적 이유로 탈취를 당했을때 수명이 길 경우 권한 남용이 발생할 수있음

#### 리프레쉬 토큰은 엑세스 토큰과 달리 안전한 곳에서 보관되며 짧은 엑세스 토큰을 계속 갱신하는 방식
-> Access Token이 만료된 경우에만 서버로 전송되어 재발급 요청을 함.


## Refresh Token 인증 프로세스

1. 로그인 요청: 사용자가 아이디/비밀번호로 로그인을 요청
2. 토큰 발급 및 저장: 서버는 검증 후 유효기간이 짧은 Access Token과 유효기간이 긴 Refresh Token을 생성
3. 클라이언트에게 두 토큰을 전달. (보안을 위해 Refresh Token은 HttpOnly, Secure 옵션이 적용된 쿠키로 보냄)
4. API 요청: 클라이언트는 API를 호출할 때마다 Authorization 헤더에 Access Token을 넣어 보냄
5. 토큰 만료 (401 에러): 시간이 지나 Access Token이 만료되면 서버는 401 Unauthorized 에러를 반환
6. 토큰 재발급 요청: 클라이언트는 에러를 확인하고, 저장해둔 Refresh Token을 담아 서버의 /api/v1/auth/refresh 같은 재발급 엔드포인트로 요청을 보냄
7. 토큰 검증 및 재발급: 서버는 Refresh Token의 유효성을 검증하고, 서버 DB/Redis에 저장된 토큰과 일치하는지 확인한 후, 새로운 Access Token을 발급

## Refresh Token은 Spring에서 어떻게 구현할까?
1. Token Provider - jjwt 라이브러리 등을 활용하여 토큰을 생성하고 파싱하는 컴포넌트입니다. Access와 Refresh 각각 생성 메서드를 둠
```java
// 예시 개념 코드
public String createAccessToken(Long userId) {
    return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME)) // 30분
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
}

public String createRefreshString(Long userId) {
    return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME)) // 14일
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
}
```
2. Refresh Token 저장소 - Refresh Token은 유효기간이 길기 때문에 사용자가 로그아웃하거나, 토큰이 탈취되었을 때 서버 측에서 강제로 만료시킬 수 있어야 함
3. Spring Security Filter 및 재발급 컨트롤러
    - 모든 요청에서 Access Token을 검증
    - 만약 Access Token이 만료되었다면 401 에러가 발생하게 유도
4. Reissue Controller (재발급 엔드포인트)
    - ex: /api/v1/auth/refresh 와 같은 경로를 Spring Security에서 우회(PermitAll) 시켜둔 뒤, 클라이언트가 보낸 Refresh Token을 받아 검증
```java
@PostMapping("/auth/refresh")
public ResponseEntity<?> reissue(@CookieValue("refreshToken") String refreshToken) {
    // 1. Refresh Token 유효성 및 만료 여부 검증
    // 2. Redis/DB에 저장된 토큰과 일치하는지 확인
    // 3. 일치한다면 새로운 Access Token 생성 후 반환
}
```
