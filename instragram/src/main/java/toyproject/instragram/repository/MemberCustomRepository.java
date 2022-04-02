package toyproject.instragram.repository;

import java.util.List;

public interface MemberCustomRepository {

    public List<Profile> searchProfiles(String nickname);
}
