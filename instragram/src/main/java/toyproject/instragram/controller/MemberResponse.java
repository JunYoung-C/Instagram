package toyproject.instragram.controller;

import lombok.Getter;
import lombok.Setter;
import toyproject.instragram.repository.MemberProfileDto;

import java.util.List;

@Getter
@Setter
public class MemberResponse {

    private List<MemberProfileDto> members;
    private int count;

    public MemberResponse(List<MemberProfileDto> members) {
        this.members = members;
        this.count = members.size();
    }
}
