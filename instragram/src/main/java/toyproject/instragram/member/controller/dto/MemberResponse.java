package toyproject.instragram.member.controller.dto;

import lombok.Getter;
import toyproject.instragram.member.repository.MemberProfileDto;

import java.util.List;

@Getter
public class MemberResponse {

    private List<MemberProfileDto> members;
    private int count;

    public MemberResponse(List<MemberProfileDto> members) {
        this.members = members;
        this.count = members.size();
    }
}
