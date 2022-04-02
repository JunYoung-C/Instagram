package toyproject.instragram.repository;

import toyproject.instragram.dto.MemberProfileDto;

import java.util.List;

public interface MemberCustomRepository {

    List<MemberProfileDto> searchProfiles(String nickname);
}
