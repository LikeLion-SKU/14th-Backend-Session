Post 도메인에 대한 API 명세서 작성하기: https://curse-locket-15a.notion.site/4b5b69c8763782eaa3998140b4cf4d55?v=170b69c8763783888fa08862b3446215

api: 서로 다른 프로그램들이 데이터를 주고받는 인터페이스

#### rest란:
- 자원을 URI로 표현하고 HTTP Method로 자원을 조작하는 HTTP 기반 설계 방식

#### rest의 구성:
1. 자원: url로 표현
2. 행위: HTTP 매서드로 표현
3. 표현: 클라이언트와 서버가 데이터를 주고받는 형태(XML, JSON)

### RestAPI란?
* rest 설계원칙을 지킨 API

### HTTP 매서드의 종류:
- GET, POST, PUT, PATCH, DELETE

### PUT vs PATCH
- 멱등성: 같은 작업을 반복했을때 상태가 변하지 않는 성질

### put의 경우 데이터의 일부를 변경하는것이 아니라 전체를 통으로 갈아버리기때문에 멱등성 보장
patch의 경우 데이터의 일부만 변경하는것이 가능하기 때문에 경우에 따라서 멱등성 보장 X
- ex) 나이 + 1을 하면 patch의 경우 나이가 계속 증가함

### softDelete, hardDelete
- 삭제를 함에 있어 hardDelete를 하는것은 굉장히 위험. 데이터 복구가 불가능함.
- hardDelete를 하면 저장공간을 어느정도 확보할 수 있다는 장점이있지만 복구가 불가능 하다는 치명적 단점 존재

### API 명세서
- API의 동작 방식, Endpoint, 요청 및 응답 구조, 인증 방식 등을 정리하여
API 사용 방법을 명확하게 설명하는 문서

- 명확한 소통, 일관성 유지, 효율적인 유지보수 가능


### API명세서를 만드는 두가지 방법
1. Notion
2. Swagger

#### Swagger를 사용시 자동적으로 문서를 만들어주고, 브라우저에서 직접 테스트가 가능함.


## Bean
- 스프링이 관리하는 객체
- 의존성을 따로 주입하지 않아도 빈으로 관리되는 객체라면 자동으로 주입해줌
- 빈으로 등록된 객체라면 싱글톤 보장

## 의존성 주입이란?
: Dependency Injection (의존성 주입)으로 필요한 의존 객체를 직접 생성(new 키워드)하지 않고 외부에서 주입 받도록 하는 방식

~~~
의존성 주입 X
private final MemberRepository memberRepository = new MemberRepositoryImpl();

의존성 주입 O
private final MemberRepository memberRepository;

    public OrderServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
~~~

* DI의 이점: 객체간 연결도가 약해지며 유지보수가 용이해지고 SOLID원칙을 지킴
* 방법: 생성자 주입, 수정자 주입, 필드 주입

### 생성자 주입
- 객체가 생성될때 딱 한번 주입
- final 키워드 사용 가능으로 인한 불변성 보장
- 의존성 누락 시 오류 발생


### 수정자 주입
- 나중에 주입 가능
- 변경 가능(final 불가)
  → 불안정 증가
~~~
@Component
public class ServiceImpl {

    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
~~~

### 필드주입
- final 불가: 객체 생성 시 초기화가 아님.
- 가독성 저하: 어떤 의존성이 필요한지 한눈에 안보임
- 순수 자바코드로 테스트가 매우 어려움: 스프링 의존성 매우 높음
~~~
@Component
public class ServiceImpl {

    @Autowired
    private MemberRepository memberRepository;
}
~~~