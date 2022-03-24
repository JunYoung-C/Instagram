package toyproject.instragram.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostMemberDto {

    private Long memberId;
    private String nickname;
    private String photoPath;

    public PostMemberDto(Long memberId, String nickname, String photoPath) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.photoPath = photoPath;
    }
}
