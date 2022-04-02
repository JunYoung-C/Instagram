package toyproject.instragram.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.AppConfig;
import toyproject.instragram.dto.MemberProfileDto;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.MemberImage;

import javax.persistence.EntityManager;
import java.util.List;

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

        em.flush();
        em.clear();
    }

    @DisplayName("닉네임으로 프로필 검색 - 성공")
    @Test
    public void searchByNickname() {
        //given
        //when
        List<MemberProfileDto> findProfiles = memberRepository.searchProfiles("jun");

        //then
        assertThat(findProfiles)
                .extracting("nickname")
                .containsExactly("junyoung", "jun_young");

        assertThat(findProfiles)
                .extracting("imagePath")
                .containsExactly("encodedFile1.png", "encodedFile2.png");

        assertThat(findProfiles).hasSize(2);
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

    @DisplayName("닉네임과 정확히 일치하는 회원 조회")
    @Test
    public void findByNickname() {
        //given
        //when
        String nickname = "junyoung";
        Member findMember = memberRepository.findByNickname(nickname).orElse(null);

        //then
        assertThat(findMember.getNickname()).isEqualTo(nickname);
    }
}