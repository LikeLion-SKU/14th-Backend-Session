API 명세서
https://occipital-hound-d56.notion.site/API-a4239be16b288279afc0810a2712e478?source=copy_link

API - 소프트웨어 간 데이터를 주고 받기 위한 통신 규칙을 정의한 인터페이스


Rest - URI(자원을 식별)가 URL(자원의 위치)보다 더 큰 범위
6개로 구분
Client - Server, stateless(무상태), Uniform Interface(인터페이스 일관성), Cacheable(캐시 가능), Layered System(계층 구조), Self-Descriptiveness(자체 표현성)

3. RestAPI - RESTFul API 차이

RestAPI - REST 방식으로 만든 API

RESTFul API - 원칙을 잘 지킨 API

4. CRUD - 생성 조회 수정 삭제

5. HTTP Method 생성 수정 삭제 조회

Create - 회원가입, 로그인 요청, 게시글 작성, 댓글 작성

Read - 사용자 조회,게시글 조회, 댓글 조회

Update - 비밀번호 변경, 닉네임 변경, 게시글 수정, 댓글 수정

Put(Resource를 전체 교체) vs Patch(Resource를 일부 수정)

멱등성이 필요한 이유
요청 실패 시 재시도 가능 여부 판단 기준
멱등하지 않은 경우, 중복 요청으로 문제 발생 가능

데이터나 복잡한 내용을 포함하게 된다면 Put
여러 값을 수정해야하는데 굳이 Patch 쓸 필요는 없다.

Delete - 회원 탈퇴, 게시글 삭제, 댓글 삭제
Soft Delete vs Hard Delete

Soft Delete
데이터베이스에서 데이터를 직접 삭제하지 않고 사용자입장에서는 데이터에 접근할 수 없게 하는 방식
장점 - 복구 가능, 이력 보존, 참조 무결성 문제 완화
단점.- DB에 데이터가 계속 쌓임, 조회 시 삭제 여부 조건 필요, 관리가 복잡해질 수 있음

Hard Delete(정말 필요하지 않을 경우)
장점 - 저장 공간 확보, 데이터 관리가 단순함, 조회가 용이함
단점 - 복구 불가능, 이력 보존 어려움, 참조 관계 문제 가능

엔드포인트 설계 원칙
명사를 사용
복수형을 사용한다
계층 구조를 표현한다
마지막 슬래시를 사용하지 않는다
소문자를 사용한다
확장자를 포함하지 않는다




왜 REST는 관계 중심 URI를 사용할까?
REST는 자원 중심 설계(어떤 자원인가를 표현)
URI는 자원의 식별자(URI는 데이터를 찾기 위한 주소)
확장성과 재사용성
URI와 HTTP Method 역할 분리


API 명세서 작성 방법
각 EndPoint에 대한 요청 Parameter를 설명한다.

Bean
수동등록 - 개발자가 직접 Bean을 등록하는 방식

자동등록 - 특정 Annotation이 붙은 클래스는 Spring이 자동으로 Bean 등록

Bean 주입 방식

Setter 주입

Annotation
코드에 특별한 의미와 기능을 부여하는 기술

@Override -> 메서드 재정의 표시

Spring에서 자주 사용하는 어노테이션
@Component
@Configuration
@Bean
@Transcation

Lombok이란?
반복되는 코드를 자동으로 생성해주는 라이브러리

@Getter 필드 값을 조회할 때 사용

@Setter 필드 값을 수정할 때 사용

@Builder 객체를 생성 시 값을 하나씩 설정할 수 있는 방식, @AllArgsConstructor와 함께 사용되어 객체 생성에 활용

@NoArgsConstructor - 파라미터가 없는 기본 생성자를 자동으로 생성해주는 기능

@AllArgsConstructor - 클래스에 선언된 모든 필드를 매개변수로 받는 생성자를 자동으로 생성해주는 기능

@Transactional - 여러 DB 작업을 하나로 묶어 모두 성공하거나 모두 실패하도록 처리하는 기능
데이터의 일관성을 유지하기 위해 중간에 오류나면 롤백

