package toyproject.instragram.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.dto.QMemberProfileDto;

import java.util.List;

import static toyproject.instragram.entity.QMember.member;
import static toyproject.instragram.entity.QMemberImage.memberImage;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final int MAX_SEARCH_COUNT = 50;

    @Override
    public List<MemberProfileDto> searchProfiles(String nickname) {
        return queryFactory
                .select(new QMemberProfileDto(
                        member.id,
                        member.nickname,
                        memberImage.storeFileName.concat(".").concat(memberImage.extension)
                ))
                .from(member)
                .where(member.nickname.contains(nickname))
                .leftJoin(member.memberImage, memberImage)
                .limit(MAX_SEARCH_COUNT)
                .fetch();
    }
}
