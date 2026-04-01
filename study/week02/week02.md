# 📝 Week02 정리 (API, Gradle, MySQL)

---

## 1. API

API(Application Programming Interface)는 서로 다른 프로그램이 데이터를 주고받기 위해 사용하는 인터페이스이다. 
즉, 클라이언트와 서버가 소통하는 규칙이라고 볼 수 있다.

---

### ✔️ REST API

REST API는 HTTP 메서드를 사용하여 자원을 처리하는 방식이다. 

예시:
GET http://localhost:8080/api/hello
---

### ✔️ HTTP Method

- GET: 데이터 조회
- POST: 데이터 생성
- PUT: 데이터 전체 수정
- PATCH: 데이터 일부 수정
- DELETE: 데이터 삭제 
---

### ✔️ API 구성 요소

- HTTP Method: 요청 방식 (GET, POST 등)
- URL: 자원의 위치
- Port: 서버 접근 번호 (기본 8080)
- Endpoint: API 경로 

---

## 2. Gradle

Gradle은 프로젝트를 실행 가능한 형태로 만들어주는 빌드 자동화 도구이다.

코드 컴파일, 테스트, 패키징 등의 과정을 자동으로 처리한다.

---

### ✔️ 주요 역할

- 코드 컴파일
- 테스트 실행
- 라이브러리(의존성) 관리
- 실행 파일 생성  
---

### ✔️ 프로젝트 구조
.gradle

gradle/

build.gradle

gradlew

settings.gradle

- build.gradle: 의존성 및 설정 관리
- gradlew: 실행 파일
- settings.gradle: 프로젝트 설정
---

### ✔️ build.gradle

프로젝트에 필요한 라이브러리를 추가하는 파일이다.

gradle
```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

필요한 의존성을 자동으로 다운로드하여 사용할 수 있다. 

## 3. MySQL

MySQL은 데이터를 저장하고 관리하는 관계형 데이터베이스(RDBMS)이다.  ￼

데이터를 테이블 형태로 저장하며 SQL을 사용하여 데이터를 조작한다.

---

### ✔️ 데이터베이스 개념

•	데이터를 저장하는 공간

•	여러 사용자와 공유 가능

•	다양한 데이터 저장 가능  ￼

---

### ✔️ 기본 구조
•	Table: 데이터 저장 공간
•	Column: 속성
•	Row: 실제 데이터

---

### ✔️ 기본 명령어

### 로그인
```angular2html
mysql -u root -p
```
### 데이터베이스 생성 및 선택
```angular2html
CREATE DATABASE likelion;
USE likelion;
```
### 조회
```angular2html
SHOW DATABASES;
SHOW TABLES;
```

### 테이블 생성
```angular2html
CREATE TABLE student (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(10) NOT NULL,
  age INT
);
```
### 테이블 조작어(DML)
```angular2html
-- 삽입
INSERT INTO student (name, age) VALUES ('홍길동', 20);

-- 조회
SELECT * FROM student;

-- 수정
UPDATE student SET age=21 WHERE id=1;

-- 삭제
DELETE FROM student WHERE id=1;

```

### ✔️ 주요 개념
•	PRIMARY KEY: 주 식별자 (중복 불가)

•	NOT NULL: 값 필수

•	AUTO_INCREMENT: 자동 증가  ￼