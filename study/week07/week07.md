# 7주차 - Spring Security와 JWT

>## 인증이 필요한 이유  
>### 요청을 보낸 클라이언트가 누구인지 알아야함   


>## 인증 VS 인가
>>## 인증
>>### 신원 확인(사용자의 신원이 진짜인지 확인)
>>### 실패 시 -> 401 Unauthorized - 비로그인
>
>>## 인가
>>### 권한 검증(인증된 사용자에게 접근 권한 부여 -> 인증이 이루어져있어야함)
>>### 실패 시 -> 403 Forbidden - 권한 부족

>## Spring Security란
>>### 모든 API마다 로그인 여부와 권한을 확인하는 코드 작성 -> 필요 X
>>### Controller 도달 전, Spring Security가 보안 검사를 통해 미충족시 401, 403 Exception 반환

>## Spring Security 인증 처리 과정
>>### 1. 클라이언트 로그인 요청
>>### 2. AuthenticationFilter가 UsernamePasswordAuthenticationToken 생성
>>>### 위 토큰은 아직 인증되어있는 토큰이 아님!
>>### 3. AuthenticationFilter가 AuthenticationManager에게 인증 요청
>>>### 필터를 통해 토큰 인증
>>### 4. AuthenticationManager가 AuthenticationProvide 중 현재 인증 방식을 처리할 수 있는 Provider에게 위임
>>### 5. AuthenticationProvider가 UserDetailsService 호출하여, DB에서 사용자 정보 조회
>>### 6. UserDetailsService가 UserDetails 반환
>>>### 이 UserDetails는 Spring Security에서 제공해주는 공통 규격임
>>>### 새 클래스로 Details 인터페이스를 구현 + 유저 엔티티 필드 삽입을 통해 사용
>>### 7. UserDetailsService가 사용자 정보를 Provider에게 반환
>>>### 요청으로 들어온 비밀번호 == DB에 저장된 비밀번호(PasswordEncoder)
>>### 8. 인증 성공 결과를 AuthenticationManager에게 반환
>>### 9. AuthenticationManager가 인증 결과를 Filter에게 반환
>>>### 인증 성공 여부 + 권한레벨 반환
>>### 10. AuthenticationFilter가 인증된 사용자 정보를 SecurityContextHolder에 저장
>## 이후 Controller/Service에서 현재 로그인한 사용자가 누구인지 확인 및 사용 가능

>## CSRF VS CORS
>>## CSRF
>>### 사용자가 원하지 않은 요청이 대신 전송되는 공격
>
>>## CORS
>>### 다른 출처 허용 여부를 정하는 브라우저 정책
>>### Origin => 프로토콜 + 도메인(호스트) + 포트
>>### 하나라도 다르면? -> 다른 Origin
>>### 백엔드의 경우 프론트엔드와 포트가 다르거나, 호스트가 다른 경우 존재
>>### -> 이때 CorsConfig를 통해 특정 Origin 허용해줘야함

>## 세션 기반 인증 vs 토큰 기반 인증
>>## 세션 기반 인증
>>### → HTTP Stateless
>>### 장점 : 서버 부하 감소, 서버 및 플랫폼 확장성
>>### 단점 : 사용자를 기억하지 못 함 -> stateless이기에
>
>>## 토큰 기반 인증
>>>### 세션 방식 -> 요청 + 세션 ID(세션 저장소에서 세션 ID 검증)
>>>### JWT 방식 -> 요청 + Access Token(서명 + 만료시간 검증)
>>>### But, User는 Post 참조하지 않음

>## JWT(JSON Web Token)
>## 서버가 사용자 정보를 담아서 발급하는 서명된 토큰   
>>## 발급 과정  
>>### 1. 로그인 성공
>>### 2. 서버가 JWT 발급
>>### 3. 클라이언트 JWT 저장
>>### 4. 이후 요청마다 JWT를 함께 전송
>>### 5. 서버가 JWT를 검증하고 사용자 식별
>>>### 1) 본 서버의 토큰이 맞는지.(signature) 2) 만료시간이 넘지 않았는지.(payload) 3) 어떤 사용자의 토큰인지(payload)
>
>>## JWT 구조
>>### Header.Payload.Signature
>>### Header -> 해싱 알고리즘, 토큰 타입 저장
>>### Payload -> 사용자ID, 이메일, 권한, 만료시간 등 저장 -> Payload는 base64로 간단히 디코딩 가능 -> 민감정보 저장 XXX
>>### Signature -> 해싱 알고리즘(base64(header).base64(payload), 시크릿키)

>## Refresh Token의 사용 이유
>>## 만료시간이 너무 길다면?
>>### 토큰이 탈취 당했을 시, 공격자가 오랫동안 사용 가능
>
>>## 만료시간이 너무 짧다면?
>>### 토큰이 자주 만료되어, 계속 다시 로그인 해야함 -> stateless 방식과 다름이 없어짐
 
># JWT 동작 원리
>### 1. 로그인 요청(ID, Password)
>### 2. 검증 후, Access Token, Refresh Token 생성
>### 3. Access Token, Refresh Token 사용자 전달
>### 4. 사용자 localStorage에 저장
>### 5. 헤더에 Access Token 담아서 요청
>### 6. Access Token 검증 후, 유효하다면 응답 생성
>### 7. 사용자에게 응답 반환

># Token 설계 시 보안 고려사항
>### 1. Access Token 만료 시간은 짧게 -> 지나치게 길 경우, 탈취시 악용가능
>### 2. Refresh Token은 서버에서 관리 -> 3번의 이유 때문
>### 3. 로그아웃 시 Refresh Token 삭제 -> 탈취 위험 최소화
>### 4. 토큰에는 민감한 정보 넣지 않기 -> payload는 base64 디코딩으로 간단히 볼 수 있음
>### 5. Secret Key는 안전하게 관리하기 -> 탈취 시, payload 조작 후 무단서명하여 비인가 접근 가능
>### 6. HTTPS 사용 -> HTTP는 평문이므로, Access Token 탈취 가능

># 스프링에서의 Refresh Token 구현
>### 1. 사용자 로그인 시 Access Token(짧은 만료 시간), Refresh Token(긴 만료 시간) 동시 발행하여 전달
>### 2. 사용자는 Access Token 만료 전까지, 이를 이용해 인증 진행
>### 3. Access Token 만료 시, 서버는 사용자에게 Refresh Token 요청
>### 4. 사용자가 Refresh Token 서버에 전달
>### 5. 서버는 Refresh Token 검증 후, Access Token 재발행하여 사용자에게 전달
>### 6. Refresh Token 만료 시 로그아웃

># Access Token VS Refresh Token
>>## Access Token
>>### 사용자가 인증/인가가 필요할 경우 사용되는 토큰
>>### 만료시간 : 몇 시간 내외
>>### 특징 : 서버에서 폐기 불가능(탈취 시 위험)
> 
>>## Refresh Token
>>### 사용자가 만료된 Access Token을 재발급 받을 경우 필요한 토큰
>>### 만료 시간 : 며칠~1달 내외
>>### 특징 : 서버에서 폐기 가능