package toyproject.instragram.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static toyproject.instragram.member.entity.QMember.member;
import static toyproject.instragram.member.entity.QMemberImage.memberImage;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable) {
        return queryFactory
                .select(new QMemberProfileDto(
                        member.id,
                        member.name,
                        member.nickname,
                        memberImage.storeFileName.concat(".").concat(memberImage.extension)
                ))
                .from(member)
                .where(member.nickname.contains(nickname))
                .leftJoin(member.memberImage, memberImage)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
