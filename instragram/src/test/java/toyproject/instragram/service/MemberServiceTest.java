package toyproject.instragram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.instragram.entity.Privacy;
import toyproject.instragram.repository.MemberProfileDto;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.MemberImage;
import toyproject.instragram.repository.MemberRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        String[] nicknames = {"junyoung", "jun_young", "chanyoung", "1234"};

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
        String nickname2 = "1234"; // 숫자로 된 닉네임

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
    @ValueSource(strings = {"01011111111", "test@naver.com", "junyoung", "1234"})
    public void signIn_failById(String signInId) {
        //given
        //when
        String wrongPassword = "1111";

        //then
        assertThatThrownBy(() -> memberService.signIn(signInId, wrongPassword))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("잘못된 비밀번호입니다. 다시 확인하세요.");
    }

    @DisplayName("로그인 - 잘못된 비밀번호로 인한 실패")
    @ParameterizedTest
    @ValueSource(strings = {"1111", "1@naver.com", " "})
    public void signIn_failByPassword(String signInId) {
        //given
        String password = "1234";

        //when
        //then
        assertThatThrownBy(() -> memberService.signIn(signInId, password))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.");
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