SpringBoot 아키텍처의 흐름
-
웹 브라우저 -> (Request) 통해 Tocat을 지나 Controller -> Service -> Repository -> Entity를 통해 DB와 연결
-> 다시 Repository -> Service -> Controller -> (Response)를 통해 다시 웹 브라우저로 반환


사용자의 요청 (Resquest) 과 요청에 대한 응답은 **API**로 이루어짐

# API 와 RESTful API ?
### API
- 서로 다른 어플리케이션이 소통하기 위한 인터페이스
### RESTful API
- 자원을 표현하고 HTTP 메서드를 사용해 상태를 표현하는 API

#### HTTP 메서드 5가지
1. POST
2. GET
3. DELETE
4. PUT
5. PATCH

##### put과 patch 차이
- put
  - 리소스의 모든 것을 업데이트 하는것
- patch
  - 리소스의 일부를 업데이트 하는것

# Gradle ?
- 빌드 자동화 도구
## 빌드 자동화 도구란 ?
- 소스 코드를 실행 가능한 애플리케이션으로 만들어주는 도구

## build.gradle
- 의존성, 플러그인 같은 빌드에 대한 모든 기능들을 정의해 놓는 파일

# 데이터베이스
- 데에터들의 집합
- 종류
  - 관계형 데이터베이스
  - 비관계형 데이터베이스

# MySQL
- 관계형 데이터베이스의 한 종류

## 테이블
- 데이터베이스 안에서 실제 데이터가 저장되는 형태

## MySQL 쿼리문
- **CREATE DATABASE** + db명
  - 데이터베이스 생성 쿼리문
- **USE** + db명
  - 사용할 db로 이동
- **SHOW databases**;
  - 모든 데이터베이스 확인 가능
- **SHOW tables**;
  - 데이터베이스 안 모든 테이블 조회
- **DESC** + 테이블명
  - 테이블 정보 조회
- **SELECT * FROM** 테이블명
  - 테이블 안에 있는 모든 데이터 조회
- 

### AUTO_INCREMENT
- 열 값이 자동으로 증가하도록 해주는 것
- ex)
  - `id bigint AUTO_INCREMENT` => id값이 자동으로 1씩 증가



