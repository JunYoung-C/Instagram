package toyproject.instragram.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toyproject.instragram.member.controller.dto.MemberResponse;
import toyproject.instragram.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
    @GetMapping("/members")
    public MemberResponse searchMembers(
            @RequestParam String nickname, @PageableDefault(size = 50) Pageable pageable) {
        return new MemberResponse(memberService.searchProfiles(nickname, pageable));
    }
}
