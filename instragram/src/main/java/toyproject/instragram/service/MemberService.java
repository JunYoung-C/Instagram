package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Privacy;
import toyproject.instragram.repository.MemberProfileDto;
import toyproject.instragram.entity.Member;
import toyproject.instragram.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        validateDuplicateNickname(memberDto.getNickname());
        Privacy privacy = Privacy.create(memberDto.getPassword(), memberDto.getPhoneNumberOrEmail());
        Member member = new Member(privacy, memberDto.getNickname(), memberDto.getName());

        memberRepository.save(member);
        return member.getId();
    }

    public List<MemberProfileDto> searchProfiles(String nickname) {
        return memberRepository.searchProfiles(nickname);
    }

    private void validateDuplicateNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findByNickname(nickname);
        if (findMember.isPresent()) {
            // TODO 예외 클래스 만들면 수정하기
            throw new IllegalStateException("이미 존재하는 별명입니다.");
        }
    }
}
