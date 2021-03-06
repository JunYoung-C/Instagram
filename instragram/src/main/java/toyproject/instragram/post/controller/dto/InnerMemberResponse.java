package toyproject.instragram.post.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toyproject.instragram.member.entity.Member;

@Getter
@AllArgsConstructor
public class InnerMemberResponse {

    private Long memberId;
    private String nickname;
    private String imagePath;

    public static InnerMemberResponse from(Member member) {
        return new InnerMemberResponse(member.getId(), member.getNickname(), getImagePath(member));
    }

    private static String getImagePath(Member member) {
        return existMemberImage(member) ? member.getMemberImage().getOriginalStoreFileName() : "";
    }

    private static boolean existMemberImage(Member member) {
        return member.getMemberImage() != null;
    }
}
