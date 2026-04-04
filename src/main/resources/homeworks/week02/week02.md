# 1. API(Application Programming Interface)

API란, "서로 다른 어플리케이션이 서로 소통하는데 사용되는 인터페이스"를 지칭한다,
API는 다양한 종류가 있는데, 그 중 가장 대표적인 것이 RESTful API이다.

## (1) RESTful API

- Representational State Transfer 아키텍처를 따르는 웹 API
- 자원을 표현하고 HTTP 메서드를 사용하여 상태를 전달하는 API

## (2) API URL 구성

API의 URL은 아래와 같이 구성되어있다.

```GET http://localhost:8080/api/hello```

- ```GET```: HTTP 메소드
- ```http```: 프로토콜
- ```localhost```: 도메인
- ```8080```: 포트번호 (스프링 부트의 기본 포트는 8080이다.)
- ```api/hello```: API 엔드포인트

## (3) HTTP Method

- API를 만들기 위해서는 반드시 알아두어야 하는 5가지 HTTP 메서드가 존재한다.
  
(1) GET - 데이터 조회
(2) POST - 데이터 등록
(3) PUT - 데이터 전체 수정
(4) PATCH - 데이터 부분 수정
(5) DELETE - 데이터 삭제

# 2. Gradle

## (1) Gradle이란?

- 오픈 소스 빌드 자동화 도구
- 프로젝트의 컴파일, 테스트, 패키징, 배포 등을 수행

```
빌드 도구란?
- 소스 코드를 실행 가능한 어플리케이션으로 만들어주는 도구
```

## (2) Gradle 파일 구조 및 역할

- ```.gradle```: gradle 버전 별 엔진 및 설정 파일
- ```gradle/wrapper```: Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게 함
- ```build.gradle```: 의존성, 플러그인 설정 등 빌드에 대한 모든 기능 정의
- ```gradlew``` & ```gradlew.bat```: Unix & Windows용 실행 스크립트
- ```settings.gradle```: 프로젝트 설정 파일

# 3. MySQL

## (1) MySQL이란?

- 전세계적으로 가장 널리 사용되고 있는 오픈소스 관계형 데이터베이스 (RDBMS)

## (2) 테이블이란?

- 관계형 데이터베이스 안에서 실제 데이터가 저장되는 형태

## (3) 명령어

### 데이터베이스 생성

- ```CREATE DATABASE [DB명]```
    - 데이터베이스 생성
- ```USE [DB명]```
  - 사용할 DB로 이동

### 데이터베이스 조회

- ```SHOW DATABASES```
  - 모든 DB 확인 가능

### 테이블 생성

- ```CREATE TABLE [테이블명] (속성1, 속성2, ...)```
```
CREATE TABLE student (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    age int
    );
```
- 컬럼별로 적용할 수 있는 CONSTRAINT 예시
  - ```NOT NULL```
    - : 해당 열이 NULL 값을 가지지 않도록 설정
  - ```AUTO_INCREMENT```
    - : 열 값이 자동으로 증가하도록 설정
  - ```PRIMARY KEY (id)```
    - : 각 형을 식별하는 키
    - : 중복 허용 X, NULL 값 가질 수 없음

### 테이블 조회

- ```SHOW TABLES```
  - 데이터베이스 안 모든 테이블 조회
- ```DESC [테이블명]```
  - 테이블 정보 조회
  
### 데이터 삽입

- ```INSERT INTO [테이블명] (컬럼1, 컬럼2 ...) VALUES (값1, 값2...);```

### 데이터 조회

- ```SELECT * FROM [테이블명]```
  - 테이블에 있는 모든 데이터 조회
- ID 컬럼은 자동으로 증가함

### 데이터 수정

- ```UPDATE [테이블명] SET [컬럼명] = [새로운 값] WHERE [조건]```
- WHERE을 사용하여 수정하려는 데이터 지정

### 데이터 삭제

- ```DELETE FROM [테이블명] WHERE [조건]```
- WHERE을 사용하여 삭제하려는 데이터 지정