# Week 02

## 1. API란 무엇일까?
API(Application Programming Interface)는 서로 다른 소프트웨어가 정보를 주고받을 수 있도록 해주는 인터페이스이다.  
예를 들어, 프론트엔드가 서버에 사용자 정보를 요청하면 서버는 API를 통해 데이터를 전달한다.
간단하게 주방의 흐름과정 느낌으로 알아보면 될 듯, 요청들에 대한 처리~~

### API의 특징
- 요청(Request)과 응답(Response) 구조로 동작한다.
- 클라이언트와 서버 간의 통신에 자주 사용된다.
- JSON 형식으로 데이터를 주고받는 경우가 많다.

### API 예시
- 로그인 요청
- 회원정보 조회
- 게시글 목록 불러오기

---

## 2. Gradle이란?
Gradle은 오픈 소스 빌드 자동화 도구다.
프로젝트의 컴파일, 테스트, 패키징, 배포 등을 자동화할 수 있다.
- 빌드 도구 : 소스 코드를 실행 가능한 어플리케이션으로 만들어주는 도구.

### Gradle의 역할
- 필요한 라이브러리 의존성 관리
- 프로젝트 빌드 자동화
- 실행 및 테스트 지원

### Gradle 파일 구조 및 역할
- .gradle : gradle 버전 별 엔진 및 설정 파일
- gradle/wrapper : Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게 함.
- build.gradle : 의존성, 플로그인 설정 등 빌드에 대한 모든 기능 정의
- gradlew & gradlew.bat : Unix & Windows용 실행 스크립트
- settings.gradle : 프로젝트 설정 파일

### 예시
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```
---

## 3. MySQL이란?
MySQL은 데이터를 저장하고 관리하는 관계형 데이터베이스 관리 시스템(RDBMS).
웹 서비스에서 회원 정보, 게시글, 댓글 등의 데이터를 저장할 때 자주 사용된다.
- 테이블이란? : 관계형 데이터베이스 안에서 실제 데이터가 저장된 형태.

### 예시
```sql
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100)
);
```
``` sql
SELECT * FROM user; 
```