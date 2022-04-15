package toyproject.instragram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.instragram.common.exception.ExceptionType;
import toyproject.instragram.common.exception.inheritance.IncorrectPasswordException;
import toyproject.instragram.common.exception.inheritance.notfoundaccount.NotFoundAccountByNicknameException;
import toyproject.instragram.common.exception.inheritance.notfoundaccount.NotFoundAccountException;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.service.MemberDto;
import toyproject.instragram.member.service.MemberService;
import toyproject.instragram.member.repository.MemberProfileDto;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.repository.MemberRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static toyproject.instragram.common.exception.ExceptionType.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        String[] phoneNumberOfEmails = {"01011111111", "01022222222", "test@naver.com", "hello@naver.com"};
        String[] nicknames = {"junyoung", "jun_young", "chanyoung", "a1234"};

        for (int i = 0; i < 4; i++) {
            Member member = new Member(
                    Privacy.create("1234", phoneNumberOfEmails[i]),
                    nicknames[i],
                    "이름" + i);
            memberRepository.save(member);
        }
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void signUp() {
        //given
        MemberDto memberDto =
                new MemberDto("email@naver.com", "name", "nickname", null);

        //when
        Long memberId = memberService.signUp(memberDto);

        //then
        Member findMember = memberRepository.findById(memberId).orElse(null);
        assertThat(findMember.getName()).isEqualTo("name");
        assertThat(findMember.getMemberImage().getOriginalStoreFileName()).isEqualTo("basic-profile-image.png");
    }

    @DisplayName("회원가입 - 별명 중복으로 인한 실패")
    @Test
    public void signUp_fail() {
        //given
        MemberDto memberDto =
                new MemberDto("email@naver.com", "이름", "junyoung", null);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 별명입니다.");
    }

    @DisplayName("로그인 - 성공")
    @Test
    public void signIn() {
        //given
        String phoneNumber = "01011111111";
        String email = "test@naver.com";
        String nickname1 = "junyoung";
        String nickname2 = "a1234"; // 숫자로 된 닉네임

        String password = "1234";

        //when
        Member findMemberByPhoneNumber = memberService.signIn(phoneNumber, password);
        Member findMemberByEmail = memberService.signIn(email, password);
        Member findMemberByNickname1 = memberService.signIn(nickname1, password);
        Member findMemberByNickname2 = memberService.signIn(nickname2, password);

        //then
        assertThat(findMemberByPhoneNumber.getPrivacy().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(findMemberByEmail.getPrivacy().getEmail()).isEqualTo(email);
        assertThat(findMemberByNickname1.getNickname()).isEqualTo(nickname1);
        assertThat(findMemberByNickname2.getNickname()).isEqualTo(nickname2);
    }

    @DisplayName("로그인 - 잘못된 아이디로 인한 실패")
    @ParameterizedTest
    @ValueSource(strings = {"1111", "1@naver.com", " "})
    public void signIn_failById(String signInId) {
        //given
        String password = "1234";

        //when
        //then
        assertThatThrownBy(() -> memberService.signIn(signInId, password))
                .isInstanceOf(NotFoundAccountException.class);
    }
    
    @DisplayName("로그인 - 잘못된 비밀번호로 인한 실패")
    @ParameterizedTest
    @ValueSource(strings = {"01011111111", "test@naver.com", "junyoung", "a1234"})
    public void signIn_failByPassword(String signInId) {
        //given
        //when
        String wrongPassword = "1111";

        //then
        assertThatThrownBy(() -> memberService.signIn(signInId, wrongPassword))
                .isInstanceOf(IncorrectPasswordException.class)
                .hasMessage(INCORRECT_PASSWORD.getException().getMessage());
    }

    @DisplayName("닉네임으로 프로필 검색")
    @Test
    public void searchProfiles() {
        //given
        //when
        List<MemberProfileDto> findProfiles = memberService.searchProfiles("you");

        //then
        assertThat(findProfiles)
                .extracting("nickname")
                .containsExactly("junyoung", "jun_young", "chanyoung");
    }
}