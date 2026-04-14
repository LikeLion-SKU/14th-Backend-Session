Entity : DB테이블과 매핑되는 클래스

JPA : 자바에서 객체와 데이터베이스를 매핑하기 위한 표준 API

Annotation
@Entity
@Id
@Column



엔터티 작성 시 반복 코드 문제

생성자 : 객체 생성 시 초기값 설정, JPA는 기본 생성자 필수
Getter : 객체의 값을 외부에서 읽기 위해 필요
Builder 객체를 쉽게 생성할 때 필요

해결방법 - Lombok(반복적으로 작성해야 하는 생성자,Getter,Builder를 쉽게 관리함)

Gradle 의존성 스코프
라이브러리가 필요한 시점을 구분하기 위한 방법

Implementation

Compileonly

annotationProcessor

runtimeOnly

testImplementation

testRuntimeOnly

클래스 레벨 어노테이션
MappedSuperclass - 해당 클래스를 테이블로 생성하지않고 상속받은 자식 엔터티에 필드가 포함
EntityListeners - 엔티티의 생성 수정 이벤트를 감지하여 Auditiing 기능이 자동 동작하도록 설정

필드 레벨 어노테이션
CreatedDate - 엔티티가 처음 저장될 때 현재 시간을 자동으로 저장
LastModifiedDate - 엔티티가 수정될 때 현제 시간을 자동으로 갱신


Controller - 클라이언트와 서버 사이를 연결하는 역할

HTTP 상태코드


ResponseEntity - 개발자가 HTTP 응답 전체를 직접 제어할 수 있는 클래스


Controller Annotation
@Tag - Controller 단위로 API를 그룹화하고 설명
@Operation - 개별 API EndPoint의 목적