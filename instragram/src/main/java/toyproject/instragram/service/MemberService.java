package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.dto.MemberProfileDto;
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
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<MemberProfileDto> searchProfiles(String nickname) {
        return memberRepository.searchProfiles(nickname);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByNickname(member.getNickname());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
