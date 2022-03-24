package toyproject.instragram.dto;

import lombok.Data;

@Data
public class MemberDto {

    private Long memberId;
    private String nickname;
    private String photoPath;

    public MemberDto(Long memberId, String nickname, String photoPath) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.photoPath = photoPath;
    }
}
