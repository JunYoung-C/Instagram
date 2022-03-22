package toyproject.instragram.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Profile;
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
        Profile profile1 = new Profile("junyoung", "이름1", null);
        Member member1 = new Member(null, profile1);
        memberRepository.save(member1);

        Profile profile2 = new Profile("jun_young", "이름2", null);
        Member member2 = new Member(null, profile2);
        memberRepository.save(member2);

        Profile profile3 = new Profile("chanyoung", "이름3", null);
        Member member3 = new Member(null, profile3);
        memberRepository.save(member3);
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void signUp() {
        //given
        Profile profile = new Profile("nickname1", "name1", null);
        Member member = new Member(null, profile);

        //when
        memberService.signUp(member);

        //then
        System.out.println(member.getId());
        Member findMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("회원가입 - 중복으로 인한 실패")
    @Test
    public void signUp_fail() {
        //given
        Profile profile1 = new Profile("junyoung", "이름1", null);
        Member member1 = new Member(null, profile1);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(member1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }

    @DisplayName("닉네임으로 프로필 검색")
    @Test
    public void searchProfiles() {
        //given
        //when
        List<Profile> findProfiles = memberService.searchProfiles("you");

        //then
        assertThat(findProfiles)
                .extracting("nickname")
                .containsExactly("junyoung", "jun_young", "chanyoung");
    }
}