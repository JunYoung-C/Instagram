package toyproject.instragram.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.instragram.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByPrivacyPhoneNumber(String phoneNumber);
    Optional<Member> findByPrivacyEmail(String email);
}
