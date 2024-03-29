# Instagram 구현
<p align="center"><img src="https://user-images.githubusercontent.com/87891581/165957434-b4f0ae25-853a-4757-aa2e-6a9386edd949.png" width="300" height="300"></p>

</br>

## :bookmark: Intro
- Spring, JPA 학습 직후 진행한 개인 프로젝트
- 직접 고민하면서 구현한 인스타그램 클론 코딩

</br>

## :calendar: 기간
- 2022년 2월 10일 ~ 4월 20일

</br>

## :eyes: 데모
<details>
<summary><b>펼쳐 보기</b></summary>
<div markdown="1">

|회원가입|로그인|
|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108709-0b3d10bb-ac69-48ca-b46b-e4923a132e22.gif" width="370" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108738-fae2f9d1-bfca-4422-a56e-e690ff961fdd.gif" width="370" height="300">|

|게시물 더보기|닉네임으로 회원 검색|
|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108747-b13fbfaf-649f-4ab0-92e5-dcfce4252e43.gif" width="370" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108753-fd2308e3-f32e-46c5-a984-76a5b48b6fbd.gif" width="370" height="300">|

|게시물 등록|게시물 수정|
|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108741-0705ce3f-3552-4371-9836-3e9d4b5f88b1.gif" width="370" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108740-b6e3a5ba-2fd8-430d-b69b-c2f055cdea78.gif" width="370" height="300">|

|댓글 등록 및 삭제|답글 등록 및 삭제|
|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108745-4f148f7e-fa8f-41b5-a2e3-83ebff3cb52c.gif" width="370" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108731-e702bcf4-de5f-41f2-a9ff-37b2dbc8536d.gif" width="370" height="300">|

</div>
</details>

</br>

## :zap: 사용 기술
### 1) Back-end
- Java 11
- Spring Boot 2.6.3
- Spring Data JPA
- QueryDSL
- Thymeleaf
- Gradle
- H2
- JUnit5
- Mockito

### 2) Front-end
- HTML
- CSS
- JavaScript

</br>

## :pushpin: ERD 설계

![image](https://user-images.githubusercontent.com/87891581/166134604-5b94d7e3-c4ca-4adf-b77e-9fd702e81ad1.png)

</br>

## :page_facing_up: 기능 목록

- 회원 기능
  - 회원 등록
  - 회원 조회
- 게시물 기능
  - 게시물 등록
  - 게시물 목록 조회
  - 게시물 조회
  - 게시물 수정
  - 게시물 삭제
- 댓글 기능
  - 댓글 등록
  - 댓글 목록 조회
  - 댓글 삭제
- 답글 기능
  - 답글 등록
  - 답글 목록 조회
  - 답글 삭제

</br>

## :mag_right: 트러블 슈팅
### 1. 게시물 N + 1 문제 해결
### :question: Question
게시물 엔티티가 게시물 이미지 리스트와 멤버 엔티티를 참조하는 상황에서, 게시물을 조회하면 추가 쿼리가 너무 많이 발생한다.

### :bulb: Answer
다대일 관계인 멤버 엔티티는 `@EntityGraph`를 사용하여 한 번에 조회하도록 하였고,
일대다 관계의 게시물 이미지 리스트는 지연 로딩을 유지하고 batch size 설정을 사용하여 In절로 한 번에 조회하도록 하였다.

결과적으로 수십번 발생하던 쿼리가 최초 게시물 조회 쿼리 한 번과 게시물 리스트 In절 쿼리 총 두 번으로 감소하였다.
### 2. DB에서 조회한 Entity를 dto로 변환하는 로직이 어떤 계층에 와야 할까?
### :question: Question
DB에서 조회한 Entity를 그대로 JSON으로 반환하는 것은 좋지 않다. 엔티티에 프레젠테이션 계층을 위한 로직이 추가되고, 불필요한 정보가 노출될 수 있기 때문이다. 이런 이유로 인해 엔티티를 dto로 변환해야 하는데, 어떤 계층에서 변환하는 것이 바람직할까?

### :bulb: Answer
먼저 OSIV를 true로 할지 false로 할지 결정해야 한다. 왜냐하면 OSIV를 끈다면, 모든 지연 로딩을 트랜잭션 안에서 처리해야 하므로 dto 변환을 Controller에서 할 수 없다. 그러면 어떤 선택을 해야 할까?
#### 1) OSIV에 대한 고민
만약 실제 인스타그램이라면, OSIV를 true로 설정하면 트래픽이 상당한 서비스라 커넥션이 부족해질 것이다. 하지만 내 프로젝트는 트래픽이 사실상 없다고 봐도 무방하므로 OSIV를 true로 설정하겠다.
> 1번 결론 : OSIV를 true로 설정할 것이기 때문에 Controller를 포함하여 고민해보자
#### 2) 변환 로직 위치에 대한 고민
우선 엔티티는 핵심 비즈니스이기 때문에 대부분의 로직은 엔티티가 필요하다. 이런 상황에서 특정 계층에 엔티티의 의존성을 제한하는 것은 유연하지 못하다고 생각한다. 또한, dto는 api를 사용하는 클라이언트와 연관성이 있으므로 보다 가까운 위치인 Controller에 오는 것이 설계상 바람직하다고 생각한다.

> 최종 결론 : Entity를 dto로 변환하는 로직은 기본적으로 Controller에 둔다. 하지만 OSIV를 false로 하거나 여러 controller에서 같은 dto를 사용하는 등의 상황에서는 타 계층에서 변환하도록 하자

</br>

<details>
<summary><b>참고 자료</b></summary>
<div markdown="1">

- [Dto 사용시기에 대한 질문 - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/139564)
- [DTO 변환 시 우아한형제들은 어떻게 처리하시나요? - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/15292)
- [dto의 layer에대해 질문 드립니다. - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/53023)
- [궁금합니다. - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/30618)

</div>
</details>
<div markdown="1">

---

### 3. 테스트 코드 실행 속도가 느리다.
### :question: Question
- `@SpringBootTest`로 테스트 코드를 작성하면 편리하지만, 테스트에 사용되지 않는 bean까지 모두 로드하기 때문에 속도가 느리다. 속도를 개선하려면 어떻게 해야 할까?
<details>
<summary><b>기존 코드</b></summary>

``` java
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    // 이하 생략
   
    @DisplayName("회원가입 - 별명 중복으로 인한 실패")
    @Test
    public void signUp_fail() {
        //given
        MemberDto memberDto =
                new MemberDto("email@naver.com", "이름", "junyoung", null);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberDto))
                .isInstanceOf(CustomFormException.class);
    }

   // 이하 생략
} 
```

</div>
</details>


### :bulb: Answer
- mockito를 사용하면 원하는 행위만 검증할 수 있으므로, 빠르게 테스트를 실행할 수 있다.

<details>
<summary><b>개선된 코드</b></summary>
<div markdown="1">

```java
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("회원가입")
    @Nested
    class signUpTest {
        // 이하 생략
  
        @DisplayName("실패 - 닉네임 중복")
        @Test
        void failByDuplicateNickname() {
            //given
            String nickname = "nickname";
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    nickname,
                    "name");

            when(memberRepository.findByNickname(nickname))
                    .thenReturn(Optional.of(member));

            MemberDto memberDto =
                    new MemberDto("email@naver.com", "name", "nickname", "1234");

            //when
            //then
            assertThatThrownBy(() -> memberService.signUp(memberDto))
                    .isExactlyInstanceOf(DuplicateNicknameException.class);
        }
  
        // 이하 생략
    }
  
    // 이하 생략
}
```

</div>
</details>

</br>

<details>
<summary><b>참고 자료</b></summary>
<div markdown="1">

- [7가지 유닛테스트 네이밍](https://it-is-mine.tistory.com/3)
- [Spring TEST 종류](https://lalwr.blogspot.com/2019/09/spring-test.html)
- [Spring Guide - 테스트 전략](https://cheese10yun.github.io/spring-guide-test-1/)
- [JUnit5 완벽 가이드](https://donghyeon.dev/junit/2021/04/11/JUnit5-%EC%99%84%EB%B2%BD-%EA%B0%80%EC%9D%B4%EB%93%9C/)
- [mockito 사용법](https://jdm.kr/blog/222)
- [Mockito 사용하기1](https://velog.io/@znftm97/Mockito-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B01)
</div>
</details>

---

</br>

## :file_folder: 자문자답하며 고민한 이슈들
- [이번 프로젝트를 진행하는 이유는?](https://github.com/JunYoung-C/Instagram/issues/1)
- [프로젝트 주제는?](https://github.com/JunYoung-C/Instagram/issues/3)
- [프로젝트 제작 전 할 일](https://github.com/JunYoung-C/Instagram/issues/4)
- [폴더 구조는 어떤 식으로 잡아야 할까?](https://github.com/JunYoung-C/Instagram/issues/9)
- [회원 관련 기능 구현](https://github.com/JunYoung-C/Instagram/issues/15)
- [게시물 관련 기능 구현](https://github.com/JunYoung-C/Instagram/issues/16)
- [댓글 관련 기능 구현](https://github.com/JunYoung-C/Instagram/issues/17)
- [api 작성](https://github.com/JunYoung-C/Instagram/issues/22)
- [Entity를 dto로 변환하는 로직은 어떤 계층에 와야할까?](https://github.com/JunYoung-C/Instagram/issues/24)
- [ajax를 적용해서 동적인 페이지로 만들기](https://github.com/JunYoung-C/Instagram/issues/29)
- [게시물을 ajax로 가져오면 기존 게시물 슬라이드 버튼 이벤트가 작동하지 않는 현상](https://github.com/JunYoung-C/Instagram/issues/30)
- [게시물 등록 시 이미지 업로드 실패](https://github.com/JunYoung-C/Instagram/issues/34)
- [로그인, 회원가입, 메인 페이지 예외 처리하기](https://github.com/JunYoung-C/Instagram/issues/38)
- [회원 가입한 회원이 게시물을 등록하고 수정하려할 때 권한이 없는 현상](https://github.com/JunYoung-C/Instagram/issues/40)
- [테스트 코드 보완하기](https://github.com/JunYoung-C/Instagram/issues/43)

</br>

## :closed_book: 경험한 것
- Front-End : 게시물 페이지, 게시물 상세 페이지, 게시물 수정 페이지, 로그인, 회원가입
- Back-End
    - 게시물, 댓글, 답글, 회원 관련 기능 구현 - [URI 문서](https://github.com/JunYoung-C/Instagram/wiki/URI-%EB%AC%B8%EC%84%9C-ver.2)
    - 테스트 코드 작성
        - 107개의 테스트 코드
        - 통합 테스트에서 `Mockito`를 사용한 단위 테스트로 변경하여 속도 개선
    - form과 api 예외를 각각 처리
        - form 예외는 `Bean Validation` 또는 `try ~ catch`로 잡은 후 Front 단에서 관련 에러 메시지 출력
        - api 예외는`@RestControllerAdvice`와 `@ExceptionHandler`로 한곳에서 처리
