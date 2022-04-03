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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = AppConfig.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @DisplayName("닉네임으로 프로필 검색 - 최대 50건 검색 성공")
    @Test
    public void searchByNickname() {
        //given
        IntStream.range(0, 51).forEach(i -> {
            Member member = new Member(null, "nickname" + i, "이름");
            member.addProfileImage(new MemberImage("file" + i, "encodedFile" + i, "png"));
            memberRepository.save(member);
        });

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

    @DisplayName("닉네임과 정확히 일치하는 회원 조회")
    @Test
    public void findByNickname() {
        //given
        IntStream.range(0, 3).forEach(i -> {
            Member member = new Member(null, "nickname" + i, "이름");
            member.addProfileImage(new MemberImage("file" + i, "encodedFile" + i, "png"));
            memberRepository.save(member);
        });

        //when
        String nickname = "nickname0";
        Member findMember = memberRepository.findByNickname(nickname).orElse(null);

        //then
        assertThat(findMember.getNickname()).isEqualTo(nickname);
    }
}