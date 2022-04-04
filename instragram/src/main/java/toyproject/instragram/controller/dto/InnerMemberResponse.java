package toyproject.instragram.controller.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.instragram.entity.Member;

@Getter
@Setter
public class InnerMemberResponse {

    private Long memberId;
    private String nickname;
    private String imagePath;

    public InnerMemberResponse(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.imagePath = member.getMemberImage().getStoreFileName();
    }
}
