package toyproject.instragram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        MemberImage memberImage1 = new MemberImage("file1", "encodedFile1", "png");
        Member member1 = new Member(null, "junyoung", "이름1");
        member1.addProfileImage(memberImage1);
        memberRepository.save(member1);

        MemberImage memberImage2 = new MemberImage("file2", "encodedFile2", "png");
        Member member2 = new Member(null, "jun_young", "이름2");
        member2.addProfileImage(memberImage2);
        memberRepository.save(member2);

        MemberImage memberImage3 = new MemberImage("file3", "encodedFile3", "png");
        Member member3 = new Member(null, "chanyoung", "이름2");
        member3.addProfileImage(memberImage3);
        memberRepository.save(member3);
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void signUp() {
        //given
        Member member = new Member(null, "nickname", "이름");
        //when
        Long memberId = memberService.signUp(member);

        //then
        Member findMember = memberRepository.findById(memberId).orElse(null);
        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("회원가입 - 중복으로 인한 실패")
    @Test
    public void signUp_fail() {
        //given
        Member member = new Member(null, "junyoung", "이름1");

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");
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