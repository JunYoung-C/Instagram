package toyproject.instragram.member.repository;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberProfileDto {
    private Long memberId;
    private String nickname;
    private String imagePath;

    @QueryProjection
    public MemberProfileDto(Long memberId, String nickname, String imagePath) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.imagePath = imagePath;
    }
}
