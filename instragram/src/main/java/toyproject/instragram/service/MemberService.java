package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Profile;
import toyproject.instragram.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    public List<Profile> searchProfiles(String nickname) {
        return memberRepository.searchProfiles(nickname);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByProfileNickname(member.getProfile().getNickname());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
