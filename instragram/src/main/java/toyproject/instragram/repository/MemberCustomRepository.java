package toyproject.instragram.repository;

import java.util.List;

public interface MemberCustomRepository {

    List<MemberProfileDto> searchProfiles(String nickname);
}
