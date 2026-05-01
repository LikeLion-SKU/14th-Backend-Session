## Repository란 ?
- Database와 통신하는 계층
- Jpa를 상속 받음 => CRUD 동작이 가능함

### JPA?
- 자바에서 ORM 기술의 표준으로 사용되는 **인터페이스** 모음
  - Jpa 스펙에 맞춰 메서드명을 만들면 직관적으로 DB에 접근할 수 있음

#### ORM ?
- 관계형 데이터베이스와 객체를 연결해주는 기술
- SQL 쿼리문을 작성하지 않아도 DB와 상호작용하게 도와줌

#### JDBC 와 JPA 차이
- JDBC
  - 데이터베이스와 통신하기 위한 API
  - 직접 SQL 작성해야됨
- JPA
  - 객체를 통해서 DB 조작
  - **JPQL** 사용

#### SQL 과 JPQL 차이
- SQL
  - **테이블명**과 ****컬럼명****으로 작성
- JPQL
  - **엔티티명**과 **필드명**으로 작성

### JPA 작성 방식 2가지
1. 쿼리 메서드 방식
   - 메서드 이름으로 쿼리를 생성
   - 메서드 이름에 맞게 JPQL 쿼리가 생성됨
2. @Query 방식
   - JPQL 쿼리를 작성해 사용 (SQL 쿼리 사용 안함)
   - 상세한 DB 작업을 할때 사용

### JPA 쿼리 메서드 종류
- .save(entity)
  - 해당 entity를 DB에 저장
- .findById(id)
  - 해당 id를 가진 entity를 DB에서 반환
- .findAll()
  - 해당 entity 테이블의 모든 데이터를 조회
- .delete(entity)
  - entity를 삭제 상태로 만들어서 DB에서 삭제
- .deleteById(id)
  - 해당 id를 가진 entity를 DB에서 삭제
- .save(entities)
  - 여러 entity를 한 번에 DB에 저장

## DTO
- 데이터 전송 객체를 의미
- 데이터 전송을 위해 도메인 모델 대신 사용되는 객체
- request 와 response로 나뉨

### @Builder
- 생성자는 매개변수 순서의 영향을 받지만 **@Builder는 매개변수 순서의 영향을 받지 않음**

## Service
- 비즈니스 로직을 담당
- Controller는 Service를 호출 -> Service는 Repository를 호출함

### Service에서 사용되는 어노테이션
- @Service
  - 비즈니스 로직을 수행하는 서비스라고 알리는 어노테이션
  - 내부에 @Component 포함
- @Transactional
  - public 메서드에서만 호출

#### 트랜잭션이란 ?
- 데이터 전송과 관련해 안전성을 확보하기 위한 방법
- 데이터 전송이 실패 -> 원 상태로 복구 (Rollback)
- 데이터 전송이 성공 -> 결과 반영 (Commit)

#### @Transactional 선언 위치
- 클래스 단에서 선언
  - 해당 클래스 내 public 메서드에 자동 적용
- 메서드 단에서 선언
  - 해당 메서드에만 적용

## Controller 어노테이션
- @Parameter
  - Swagger 어노테이션과 요청 파라미터의 메타데이터 정의
- @RequestBody
  - HTTP 요청 Body(JSON 등)를 Java 객체로 매핑
- @PathVariable
  - URL 경로에 포함된 값을 메서드 파라미터로 바인딩

## 영속성 컨텍스트 ?
- entity를 영구 저장하는 환경

### Entity의 생명 주기
- 비영속 상태
  - 영속성 컨텍스트와 관련 없는 새로운 상태
  - **객체를 새로 생성**할때 -> 비영속 상태
- 영속 상태
  - 영속성 컨텍스트에서 관리 되는 상태
  - **객체를 저장** -> 영속 상태
- 준영속 상태
  - 영속성 컨텍스트에 저장됐다가 분리된 상태
- 삭제 상태
  - 영속성 컨텍스트에서 삭제된 상태

### 영속성 컨텍스트의 특징
- 1차 캐싱
- 트랜잭션을 지원하는 쓰기 지연
- 변경 감지
- 동일성 보장

### Flush (플러시)
- 영속성 컨텍스트의 변경 내용을 DB에 반영하는 것
- Transactional이 commit 될 때 flush가 내부적으로 발생
- 



