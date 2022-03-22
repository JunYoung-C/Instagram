package toyproject.instragram.repository;

import toyproject.instragram.entity.Profile;

import java.util.List;

public interface MemberCustomRepository {

    public List<Profile> searchProfiles(String nickname);
}
