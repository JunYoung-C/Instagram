package toyproject.instragram.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toyproject.instragram.AppConfig;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.MemberImage;
import toyproject.instragram.entity.Privacy;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        IntStream.range(0, 51).forEach(i -> {
            Privacy privacy = null;
            if (i > 20) {
                privacy = Privacy.create("1234", String.valueOf(i));
            } else {
                privacy = Privacy.create("1234", i + "@naver.com");
            }
            Member member = new Member(privacy, "nickname" + i, "이름");
            member.addProfileImage(new MemberImage("file" + i, "encodedFile" + i, "png"));
            memberRepository.save(member);
        });
    }

    @DisplayName("닉네임으로 프로필 검색 - 최대 50건 검색 성공")
    @Test
    public void searchByNickname() {
        //given
        //when
        List<MemberProfileDto> findProfiles = memberRepository.searchProfiles("ick");

        //then
        assertThat(findProfiles).hasSize(50);
    }

    @DisplayName("닉네임으로 프로필 검색 - 결과 없음")
    @Test
    public void searchByNickname_fail() {
        //given
        //when
        List<MemberProfileDto> findProfiles = memberRepository.searchProfiles(" ");

        //then
        assertThat(findProfiles.size()).isEqualTo(0);
    }

    @DisplayName("핸드폰 번호가 정확히 일치하는 회원 조회")
    @Test
    public void findByPrivacyPhoneNumber() {
        //given
        //when
        String phoneNumber = "21";
        Member findMember = memberRepository.findByPrivacyPhoneNumber(phoneNumber).orElse(null);

        //then
        assertThat(findMember.getPrivacy().getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @DisplayName("이메일이 정확히 일치하는 회원 조회")
    @Test
    public void findByPrivacyEmail() {
        //given
        //when
        String email = "0@naver.com";
        Member findMember = memberRepository.findByPrivacyEmail(email).orElse(null);

        //then
        assertThat(findMember.getPrivacy().getEmail()).isEqualTo(email);
    }

    @DisplayName("닉네임이 정확히 일치하는 회원 조회")
    @Test
    public void findByNickname() {
        //given
        //when
        String nickname = "nickname0";
        Member findMember = memberRepository.findByNickname(nickname).orElse(null);

        //then
        assertThat(findMember.getNickname()).isEqualTo(nickname);
    }
}