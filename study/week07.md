인증이 필요한 이유
-> 요청을 보낸 클라이언트가 누군지에 대한 답변

인증 authentication
너 누구니? -> 신원 확인
사용자의 신원이 진짜인지 확인하는 행위
상황 1. 로그인 성공
상황 2. 마이페이지 접근
상황 3. 관리자 페이지 접근

인가 authorization
너 이거 할 권환 있어? -> 권환 검증
인증 완료
인증된 사용자만 가능
인증된 사용자 + 관리자 권환 보유

Spring security
인증과 인가를 처리해주는 보안 프레임워크

원래는 api 요청하면 controller로 가는데 
security가 가로채 보안 검사

csrf(cross site request forgery) 사이트간 요청 위조
쿠키 기반 인증
:브라우저에서 요청을 보낼 때 자동으로 쿠키를 함께 전송
jwt 기반 인증
:쿠키가 아닌 Authorization 헤더에 담아 전송

cors(cross-origin Resource Sharing)
다른 출처 요청 허용 여부를 정하는 브라우저 정책

세션 기반 인증 vs 토큰 기반 인증
로그인에 성공한 사용자를 다음 요청에서 알아보는 방법

HTTP Stateless 특성
장점 : 서버 부하 감소, 서버 플랫폼 확장성
단점 : 사용자를 기억하지 못함

사용자를 식별하기 위한 방법


JWT(Json Web Token)
서버가 사용자 정보를 담아서 발급하는 서명된 토큰

1. 로그인 성공
2. 서버가 jwt 발급
3. 클라이언트가 jwt 저장
4. 이후 요청마다 jwt를 함께 전송(Authorization : Bearer accessToken)
5. 서버가 jwt를 검증하고 사용자 식별

JWT의 구조 
Header.Payload.Signature

Header
어떠한 알고리즘으로 암호화 할 것인지, 어떠한 토큰을 사용할 것인지에 대한 정보

Payload
claim이라고 부르는 전달하려는 정보

Signature
Header와 Payload를 기반으로 Secret Key를 사용해 서명 생성, 토큰을 받을 때 다시 서명을 검증

jwt를 하나만 사용하면 편의성과 보안 사이에서 문제가 발생

만료 시간이 길면, 토큰이 탈취됐을 때 공격자가 오랫동안 사용 가능
만료 시간이 짧으면, 토큰이 자주 만료되어 계속 다시 로그인해야 한다.

AccessToken
API에 접근하기 위한 출입증

RefreshToken
AccessToken을 다시 발급받기 위한 토큰