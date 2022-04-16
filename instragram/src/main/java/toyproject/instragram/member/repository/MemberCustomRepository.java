package toyproject.instragram.member.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberCustomRepository {

    List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable);
}
