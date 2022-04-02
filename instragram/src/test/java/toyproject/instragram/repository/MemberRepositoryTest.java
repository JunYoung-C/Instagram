package toyproject.instragram.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class MemberRepositoryTest {

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

    @DisplayName("닉네임으로 프로필 검색 - 성공")
    @Test
    public void searchByNickname() {
        //given
        //when
        List<Profile> findProfiles = memberRepository.searchProfiles("you");

        //then
        assertThat(findProfiles)
                .extracting("nickname")
                .containsExactly("junyoung", "jun_young", "chanyoung");

    }

    @DisplayName("닉네임으로 프로필 검색 - 결과 없음")
    @Test
    public void searchByNickname_fail() {
        //given
        //when
        List<Profile> findProfiles = memberRepository.searchProfiles(" ");

        //then
        assertThat(findProfiles.size()).isEqualTo(0);

    }

    @DisplayName("닉네임과 정확히 일치하는 회원 조회")
    @Test
    public void findByNickname() {
        //given
        //when
        String nickname = "junyoung";
        Member findMember = memberRepository.findByProfileNickname(nickname).orElse(null);

        //then
        assertThat(findMember.getProfile().getNickname()).isEqualTo(nickname);
    }
}