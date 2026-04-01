## API
- 서로 다른 어플리케이션이 서로 소통하는데 사용되는 인터페이스
> 식당에 비유하자면, 손님(프로그램)의 요청을 받아 요리사(프로그램)에게 전달하고, 완성된 요리(데이터)를 다시 손님에게 가져다주는 점원과 같은 역할
- RESTful API는 자원을 표현하고 HTTP 메서드를 사용하여 상태를 전달하는 웹 API 아키텍처
- 5가지 HTTP 메서드
    1. `GET`: 데이터 조회
    2. `POST`: 데이터 등록
    3. `PATCH`: 데이터 부분 수정
    4. `PUT`: 데이터 전체 수정
    5. `DELETE`: 데이터 삭제

### API URL 구성
### `GET http://localhost:8080/api/hello`
1. HTTP 메소드: `GET`
2. 프로토콜: `http://`
3. 도메인: `localhost`
4. 포트번호: `8080` (스프링부트 기본 포트 8080)
5. API 엔드포인트: `/api/hello`

---

## Gradle
- 오픈 소스 기반의 빌드 자동화 도구
> 빌드 도구: 소스 코드를 실행 가능한 어플리케이션으로 만들어 주는 도구
- 프로젝트의 컴파일, 테스트, 패키징, 배포 등을 수행
- `build.gradle`: 의존성, 플러그인 설정 등 빌드에 대한 모든 기능 정의

---

## MySQL
- 전 세계적으로 가장 널리 사용되고 있는 오픈소스 관계형 데이터 베이스(RDBMS)
- 관계형 데이터베이스의 특징: 구조적인 데이터 저장 방식으로, 스키마에 맞게 데이터를 입력해야 하며 SQL 언어를 사용하여 데이터를 테이블 형식으로 저장
- 테이블: 관계형 데이터베이스 안에서 실제 데이터가 저장되는 형태로, 속성을 나타내는 열(Attribute)과 실제 데이터 행(Tuple)으로 구성

### MySQL 주요 실습 코드
1) 데이터베이스 관리
```sql
CREATE DATABASE likelion; -- 데이터베이스 생성
USE likelion;             -- 사용할 데이터베이스 이동
SHOW DATABASES;           -- 모든 데이터베이스 확인 가능
```
2) 테이블 관리
```sql
-- 테이블 생성
CREATE TABLE student (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    age int
);
SHOW TABLES;       -- 데이터베이스 안 모든 테이블 조회
DESCRIBE student;  -- student 테이블 정보 조회
```
3) 데이터 관리
```sql
-- 데이터 삽입
INSERT INTO student (name, age) VALUES ('이주희', 24);

-- 데이터 조회
SELECT * FROM student;

-- 데이터 수정
UPDATE student SET age=24 WHERE id=3;

-- 데이터 삭제
DELETE FROM student WHERE name="이멋사";
```