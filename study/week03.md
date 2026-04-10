API 명세서: https://www.notion.so/API-33e42ef867b080519acef8c4d6ddd770?source=copy_link

# **REST란?**
- HTTP기반으로 HTTP method를 사용하여 URI에 정보를 담는 API 방식
- RESTful은 REST의 형식을 잘 지킨 것
- 특징:
  1. Client-Server 구조 - 백과 프론트를 나눠줌
  2. Stateless - 직전의 요청도 기억하지 않음, 매 요청마다 정보 전달 필요
  3. Uniform Interface - 구조가 일관됨(자원:URI, 행동:HTTP Method)
- 설계 원칙:
  1. 명사 사용 ex: /posts O  /creat X
  2. 복수형 사용 ex: /posts O  /post X  
  3. 계층 구조 표현 ex: /users/1/posts O 
  4. 마지막에 / 사용 금지 ex: /posts/ X
  5. 소문자 사용 ex: /Post X
  6. 확장자 포함 금지 ex: /image.jpg X

# **HTTP Metho란?**
- 클라이언트가 서버에게 어떤 동작을 할지 요청하는 방식
- 데이터 관리 및 조작에서 사용되는 기본적인 작업 4가지를 설계한다
1. Creat : POST
2. Read : GET
3. Update : PATCH & PUT
4. Delete : DELETE

### PUT vs PATCH
- 기능 차이: PUT - 데이터 전체 수정, PATCH - 데이터 부분 수정
- 특징 : PUT - 멱등성 보장 O, PATCH - 멱등성 보장 X
  - 멱등성 : 같은 요청을 여러번 해도 결과가 같은것 (예: 나이를 25으로 바꾸기 vs 나이를 한 살 더먹기)
- 멱등성은 요청 처리 중에 문제가 생겼을 때 다시 요청하여 원래의 결과를 유지해야하기 때문에 필요하다

### DELETE
- 논리적 삭제(soft-delete) vs 물리적 삭제(hard-delete)
- 논리적 삭제 : 데이터를 실제로 삭제하지 않고 사용자가 접근하지 못하도록 하여 삭제한 척 하는 것. is_delete 같은 속성을 이용함
- 물리적 삭제 : 실제로 데이터를 삭제함, 더이상 사용하지 않는 데이터를 삭제해서 DB에 공간확보
- 실무에서는 주로 논리적 삭제를 사용

# API명세서
- 명확한 소통과 일관성 유지, 유지 보수 편의성을 위해 사용
### 작성법
1. 기본 정보 작성(API이름, 버전, Base URL)
2. 인증 방식 명시
3. EndPoint와 HTTP Method 작성
4. 각 EndPoint에 대한 요청 파라미터 설명
5. API가 반환하는 응답 구조 설명
6. 사용 중 발생할 수 있는 오류와 대응법을 명시
### 문서화 도구
- **Swagger**
- GitBook
- PostMan
- Spring Rest Docs

# Bean
- 기존 JAVA의 객체 생성은 매번 new를 해줘야하기 떄문에 유지 보수가 어려웠다. 이것을 Spring Container가 관리하도록 해주는게 Bean!
- 특징:
  1. new 사용 X
  2. Spring이 생성하고 생명주기를 관리
  3. 기본적으로 싱글톤을 사용하며 동일 객체를 재사용 가능
- 효과:
  1. 객체를 한 곳에서 중앙 관리
  2. 코드의 변경사항을 쉽게 반영 가능
  3. 유지보수가 쉬워짐
- 수동 등록: `@Configuration+@Bean`, **자동 등록**: `@Component`, `@Service`, `@Controller` 등
- 수동 등록은 외부라이브러리를 등록할 수 있지만 코드가 늘어나고 유지보수가 힘들 수 있음
- 자동 등록은 외부라이브러리 등록은 못하지만 코드가 간결하고 유지보수가 유리함
### DI(Dependency Injection)
- 필요한 객체를 Spring이 대신 주입해주는 방식
- 장점:
  1. 객체간 결합도 감소
  2. 유지보수 편리
  3. 코드 테스트 간편
  4. Spring이 객체 관계 자동 연결
> Bean은 Spring가 관리하는 객체, DI는 Bean을 알맞은 곳에 주입해주는 방식
- 주입 방식:
  1. **생성자 주입** - 의존성을 생성 시점에 확정하여 안정성과 테스트 용이성을 보장함
  2. 필드 주입
  3. Setter 주입
# Annotation(@)
- 코드에 특별한 의미와 기능을 적용하는 기술
- 코드에 대한 정보와 설정을 제공하는 메타데이터 ex: @Override
- 클래스, 메서드, 필드 등 다양한 곳에서 사용됨
### Lombok
- 반복되는 코드를 자동으로 생성해주는 라이브러리
- `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor` 등
### @Transactional
- 여러 DB작업을 하나로 묶어 모두 성공하거나 모두 실패하도록 하는 기능
- ACID:
  - Atomicity(원자성) : 트랜잭션은 모두 원자적으로 처리되야함, 즉 모두 실패 혹은 모두 성공
  - Consistency(일관성) : 트랜잭션 수행 전후의 데이터는 모두 정상적인 상태를 유지해야함
  - Isolation(격리성) : 트랜잭션은 독립되어야함, 동시에 실행되는 다른 트랜잭션의 영향을 받으면 안됨
  - Durability(지속성) : 트랜잭션이 완료되면 그 결과는 영구히 유지되어야함