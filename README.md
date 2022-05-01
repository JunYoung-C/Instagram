# Instagram 구현
> 스프링과 JPA를 학습하고 체화하기 위한 개인 웹 프로젝트
<p align="center"><img src="https://user-images.githubusercontent.com/87891581/165957434-b4f0ae25-853a-4757-aa2e-6a9386edd949.png" width="300" height="300"></p>

</br>

## :bookmark: Intro
- 사진과 영상을 업로드하여 전 세계 사람들과 교류할 수 있는 SNS
- 클론 코딩 강의가 아닌, 직접 설계하고 만들어보는 인스타그램!

</br>

## :calendar: 제작 기간 & 참여 인원
- 2022년 2월 10일 ~ 4월 20일
- 개인 프로젝트

</br>

## :eyes: 
<details>
<summary><b>펼쳐 보기</b></summary>
<div markdown="1">
  
|회원가입|로그인|게시물 더보기|
|:--:|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108709-0b3d10bb-ac69-48ca-b46b-e4923a132e22.gif" width="300" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108738-fae2f9d1-bfca-4422-a56e-e690ff961fdd.gif" width="300" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108747-b13fbfaf-649f-4ab0-92e5-dcfce4252e43.gif" width="300" height="300">|

|닉네임으로 회원 검색|게시물 등록|게시물 수정|
|:--:|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108753-fd2308e3-f32e-46c5-a984-76a5b48b6fbd.gif" width="300" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108741-0705ce3f-3552-4371-9836-3e9d4b5f88b1.gif" width="300" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108740-b6e3a5ba-2fd8-430d-b69b-c2f055cdea78.gif" width="300" height="300">|

|댓글 등록 및 삭제|답글 등록 및 삭제|
|:--:|:--:|
|<img src="https://user-images.githubusercontent.com/87891581/166108745-4f148f7e-fa8f-41b5-a2e3-83ebff3cb52c.gif" width="300" height="300">|<img src="https://user-images.githubusercontent.com/87891581/166108731-e702bcf4-de5f-41f2-a9ff-37b2dbc8536d.gif" width="300" height="300">|
  
</div>
</summary>
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
### 2) DB
  - H2
### 3) test-tool
  - JUnit5
  - Mockito
### 4) Front-end
  - HTML
  - CSS
  - JavaScript

</br>

## :pushpin: 구조
### 1) 전체 흐름
<details>
<summary><b>이미지 펼쳐 보기</b></summary>
<div markdown="1">
  
  ![api 흐름](https://user-images.githubusercontent.com/87891581/166135748-6f183e5f-c3af-4d10-aa7d-7cf615f94391.png)
</div>
</summary>
</details>

### 2) ERD
<details>
<summary><b>이미지 펼쳐 보기</b></summary>
<div markdown="1">

  ![image](https://user-images.githubusercontent.com/87891581/166134604-5b94d7e3-c4ca-4adf-b77e-9fd702e81ad1.png)
</div>
</summary>
</details>

### 3) 클래스 다이어그램
<details>
<summary><b>이미지 펼쳐 보기</b></summary>
<div markdown="1">

  ![image](https://user-images.githubusercontent.com/87891581/166135741-5d56b43e-6b74-4a00-9570-1664ead8a2e4.png)
</div>
</summary>
</details>

</br>

## :page_facing_up: 기능
> [URI 문서 ver.2](https://github.com/JunYoung-C/Instagram/wiki/URI-%EB%AC%B8%EC%84%9C-ver.2)

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
### 4.1. DB에서 조회한 Entity를 dto로 변환하는 로직이 어떤 계층에 와야할까?
### :question: Question
DB에서 조회한 Entity를 그대로 JSON으로 반환하는 것은 좋지 않다. 엔티티에 프레젠테이션 계층을 위한 로직이 추가되고, 불필요한 정보가 노출될 수 있기 때문이다. 이런 이유로 인해 엔티티를 dto로 변환해야 하는데, 어떤 계층에서 변환하는 것이 바람직할까?

### :bulb: Answer
우선적으로 OSIV를 true로 할지 false로 할지 결정해야 한다. 왜냐하면 OSIV를 끈다면, 모든 지연 로딩을 트랜잭션 안에서 처리해야 하므로 dto 변환을 Controller에서 할 수 없다. 그러면 어떤 선택을 해야 할까?
#### 1. OSIV에 대한 고민
 만약 실제 인스타그램이라면, OSIV를 true로 할 경우 트래픽이 상당한 서비스라 커넥션이 부족해질 것이다. 하지만 내 프로젝트는 트래픽이 사실상 없다고 봐도 무방하기 때문에 OSIV를 true로 설정하겠다.
> 1번 결론 : OSIV를 true로 설정할 것이기 때문에 Controller를 포함하여 고민해보자
#### 2. 변환 로직 위치에 대한 고민
 우선 엔티티는 핵심 비즈니스이기 때문에 대부분의 로직은 엔티티가 필요하다. 이런 상황에서 특정 계층에 엔티티의 의존성을 제한하는 것은 유연하지 못하다고 생각한다. 또한, dto는 api를 사용하는 클라이언트와 연관성이 있기 때문에 보다 가까운 위치인 Controller에 오는 것이 설계상 바람직하다고 생각한다.
 
> 최종 결론 : 외부와의 데이터 교환에 사용되는 dto는 기본적으로 controller 계층에서 변환 로직이 온다. 하지만 여러 controller에서 같은 dto를 사용하거나, 성능상 이슈로 인해 바로 dto로 조회를 하는 경우에는 타 계층에 와도 무방하다.

</br>

<details>
<summary><b>참고 자료</b></summary>
<div markdown="1">
  
- [Dto 사용시기에 대한 질문 - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/139564)
- [DTO 변환 시 우아한형제들은 어떻게 처리하시나요? - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/15292)
- [dto의 layer에대해 질문 드립니다. - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/53023)
- [궁금합니다. - 인프런 | 질문 & 답변 (inflearn.com)](https://www.inflearn.com/questions/30618)

</div>
</summary>
</details>
<div markdown="1">

---

### 4.2. 테스트 코드 실행 속도가 느리다.
### :question: Question
- `@SpringBootTest`로 테스트 코드를 작성하면 편리하지만, 테스트에 사용되지 않는 빈들까지 모두 로드하기 때문에 속도가 느리다. 그러면 속도를 개선하려면 어떻게 해야할까?
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
</summary>
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
</summary>
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
</summary>
</details>

---

</br>

## :wrench: 개선할 점
- 기능 보완
  - 게시물, 댓글, 답글 좋아요 기능
  - 좋아요가 많은 댓글, 답글 순서로 조회
  - 동영상도 업로드 가능
  - 페이스북 로그인
  - 로그아웃
  - 회원 정보 수정
  - 팔로워, 팔로잉
  - 팔로잉한 회원의 게시물만 조회
  - 채팅
- 프로젝트 배포
- 성능 테스트 및 기능 최적화
- api 문서화 툴 도입
