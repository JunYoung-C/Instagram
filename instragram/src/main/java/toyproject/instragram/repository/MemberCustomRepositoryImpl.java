package toyproject.instragram.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.entity.Profile;

import java.util.List;

import static toyproject.instragram.entity.QMember.member;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Profile> searchProfiles(String nickname) {
        return queryFactory
                .select(member.profile)
                .from(member)
                .where(member.profile.nickname.contains(nickname))
                .fetch();
    }
}
