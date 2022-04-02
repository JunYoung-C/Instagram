package toyproject.instragram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Profile;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository{

    Optional<Member> findByNickname(String nickname);
}
