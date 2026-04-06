# 🚀 백엔드 세션 학습 노트: API, Spring Boot, Gradle, MySQL

---

## 1. API & RESTful API의 이해

### API (Application Programming Interface)
서로 다른 애플리케이션이 서로 소통하는 데 사용되는 인터페이스입니다.
* **동작 원리:** 클라이언트가 특정 URL로 요청을 보내면, 서버가 이를 받아 로직을 처리하고 데이터를 돌려주는 과정으로 이루어집니다.

### RESTful API
**R**epresentational **S**tate **T**ransfer 아키텍처를 따르는 웹 API입니다. 자원(Resource)을 표현하고 HTTP 메서드를 사용하여 상태를 전달합니다.

### API URL 구성
> `GET http://localhost:8080/api/hello`
> **HTTP 메서드 + 프로토콜 + 도메인 + 포트 번호 + API 엔드포인트**

* **프로토콜 (`http://`):** 통신 규약. HTTP라는 규칙에 맞춰 데이터를 패킷 형태로 보냅니다.
* **도메인 (`localhost`):** 컴퓨터의 주소. 원래는 IP 주소이지만 사람이 읽기 쉬운 형태로 이름을 붙인 것입니다. (`localhost`는 현재 자신의 컴퓨터를 의미)
* **포트 번호 (`8080`):** 컴퓨터 내 프로그램의 번호. (Spring Boot의 기본 점유 포트)
* **API 엔드포인트 (`/api/hello`):** 서버로 들어온 뒤 어떤 클래스의 어떤 메서드를 실행할지에 대한 목적지 정보입니다.

### HTTP 메서드 (HTTP Methods)

| 메서드 | 역할 | 설명 |
| :---: | :--- | :--- |
| **GET** | 데이터 조회 | 서버에 있는 자원을 변경하지 않고 읽어오기만 함 |
| **POST** | 데이터 등록 | 새로운 데이터를 생성함 |
| **PUT** | 데이터 전체 수정 | 기존 데이터를 통째로 교체함 |
| **PATCH** | 데이터 부분 수정 | 기존 데이터의 일부만 변경함 |
| **DELETE** | 데이터 삭제 | 데이터를 삭제함 |

---

## 2. Spring Boot Web 핵심 개념

### 주요 어노테이션 (Annotations)

* **`@RestController`**
  * `@Controller` + `@ResponseBody`의 결합. 해당 클래스가 REST API 컨트롤러임을 선언합니다.
  * > **추가 설명:** 전통적인 `@Controller`는 로직 수행 후 HTML 화면을 반환하지만, 스마트폰 앱이나 React/Vue 같은 프론트엔드 프레임워크의 등장으로 **데이터(JSON)만**을 필요로 하게 되었습니다. `@ResponseBody`는 화면 대신 알맹이 데이터만 보내도록 하며, 이를 클래스 레벨에서 한 번에 적용하는 것이 `@RestController`입니다.

* **`@RequestMapping`**
  * 클래스 내 모든 메서드들의 **공통 API 엔드포인트 경로**를 설정합니다.

* **`@GetMapping`**
  * HTTP GET 요청을 처리합니다.
  * > **추가 설명:** 해당 메서드는 Spring의 '핸들러 매핑'이라는 지도에 등록됩니다. `GET /api/hello` 요청이 들어오면, `/api`가 적힌 클래스 안의 `/hello`가 적힌 메서드를 찾아 코드를 실행합니다.

### 파라미터를 받는 2가지 대표 방식
클라이언트가 어떤 데이터를 원하는지 서버에 알려주는 방법입니다.
* **`@PathVariable`**: URL 경로의 일부를 변수로 사용할 때
  * 예: `/api/users/1` (1번 유저 조회)
* **`@RequestParam`**: URL 뒤에 쿼리 파라미터(`?key=value`)를 붙일 때
  * 예: `/api/users?name=minho` (이름이 민호인 유저 조회)

### HTTP 상태 코드 (Status Codes)
서버가 데이터를 줄 때, 요청 처리가 성공했는지 실패했는지 알려주는 정보입니다.

| 상태 코드 | 의미 | 설명 |
| :---: | :--- | :--- |
| **200 OK** | 성공 | 요청이 성공적으로 처리됨 (주로 GET) |
| **201 Created** | 생성됨 | POST 요청을 받고 새로운 데이터가 성공적으로 생성됨 |
| **400 Bad Request** | 잘못된 요청 | 클라이언트가 보낸 데이터 형식이 이상함 (예: DB 제약조건 위배) |
| **404 Not Found** | 찾을 수 없음 | 클라이언트가 요청한 URL 주소가 서버에 존재하지 않음 |
| **500 Internal Server Error** | 서버 에러 | 서버 측 코드에서 예외나 에러가 발생함 |

> 💡 **JSON:** `@RestController` 사용 시 서버가 클라이언트에게 보내는 데이터 규격. 텍스트 형태라 다른 언어와 플랫폼에서도 쉽게 읽을 수 있습니다.

---

## 3. Gradle (빌드 자동화 도구)

**빌드 도구란?** 소스 코드를 실행 가능한 애플리케이션으로 만들어주는 프로그램입니다.
**Gradle**은 프로젝트의 컴파일, 테스트, 패키징, 배포 등을 수행하는 오픈 소스 빌드 자동화 도구입니다.

### Gradle 프로젝트 주요 파일 및 폴더

* **`.gradle/`**
  * Gradle 버전별 엔진 및 설정 파일. 컴파일 내용과 다운로드한 라이브러리를 캐싱하여 저장합니다.
  * > **주의:** 이전 빌드 기록이나 버전 변경으로 충돌이 날 경우, 이 폴더를 비우고 다시 실행하면 해결되기도 합니다.
* **`gradle/wrapper/`**
  * 내 컴퓨터에 Gradle이 설치되어 있지 않아도 Gradle Task를 실행할 수 있게 해줍니다.
  * > **추가 설명:** 실행 파일(jar)과 다운로드 설정 파일이 들어 있어, 다른 컴퓨터나 팀원 간의 버전 파편화 문제를 방지합니다.
* **`build.gradle`**
  * 프로젝트 빌드에 대한 모든 기능(의존성, 플러그인 등)을 정의하는 핵심 파일입니다.
  * `plugins`: 프로젝트의 정체성 설정
  * `dependencies`: 실행에 필요한 외부 라이브러리 목록
  * `repositories`: 라이브러리를 다운로드할 원격 저장소 위치
* **`settings.gradle`**
  * 프로젝트 설정 파일. 프로젝트 이름과 빌드에 포함할 하위 폴더들을 결정합니다.
* **`gradlew` & `gradlew.bat`**
  * Gradle Wrapper 실행 스크립트. (`gradlew`: Unix/Mac용, `gradlew.bat`: Windows용)

### 주요 명령어
```bash
# 프로젝트 컴파일 및 실행 가능한 .jar 파일 생성
./gradlew build 

# 빌드된 .jar 파일 실행 (버전명은 프로젝트에 따라 다름)
java -jar build/libs/be-session-0.0.1-SNAPSHOT.jar
```

4. MySQL 기초 및 실습
   전세계적으로 널리 사용되는 오픈소스 관계형 데이터베이스입니다.

테이블(Table)이란?

- 관계형 데이터베이스 안에서 실제로 데이터가 저장되는 형태입니다.

릴레이션 스키마: 표의 구조

릴레이션 인스턴스: 실제 데이터

도메인: 값의 허용 범위

Attribute(열): 세로 한 줄

Tuple(행): 가로 한 줄

## MySQL 기본 명령어 및 실습

### MySQL 접속 (관리자 계정으로 비밀번호 입력 후 접속)

mysql -u root -p

-- 데이터베이스 생성 및 사용
CREATE DATABASE likelion;
USE likelion;
SHOW DATABASES;

### 테이블 생성 

(NOT NULL: 빈 값 방지, AUTO_INCREMENT: 자동 증가)

CREATE TABLE student (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
age INT
);

### 테이블 확인
SHOW TABLES;
DESCRIBE student;

### 데이터 조작 (CRUD)
INSERT INTO student (name, age) VALUES ('금시언', 25);

SELECT * FROM student;

UPDATE student SET name='김나경' WHERE name='김나겸';

DELETE FROM student WHERE name='이멋사';

## MySQL과 SpringBoot 연동
  
-  Edit Starters 클릭해서 의존성 추가

- MySQL Driver, Spring Data JPA 추가

- application.yml 설정

- 환경변수 설정

![img.png](Image/img.png)