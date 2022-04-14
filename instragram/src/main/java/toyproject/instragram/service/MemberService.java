package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.MemberImage;
import toyproject.instragram.entity.Privacy;
import toyproject.instragram.repository.MemberProfileDto;
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
        member.addProfileImage(createBasicProfileImage());

        memberRepository.save(member);
        return member.getId();
    }

    private MemberImage createBasicProfileImage() {
        return new MemberImage("basic-profile-image", "basic-profile-image", "png");
    }

    public Member signIn(String signInId, String password) {
        Member findMember = findBySignId(signInId);
        validateAccount(password, findMember);
        return findMember;
    }

    private void validateAccount(String password, Member findMember) {
        // TODO 예외 클래스 만들면 수정하기
        if (findMember == null) {
            throw new IllegalStateException("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.");
        }

        if (!findMember.isCorrectPassword(password)) {
            throw new IllegalStateException("잘못된 비밀번호입니다. 다시 확인하세요.");
        }
    }

    private Member findBySignId(String signId) {
        if (Privacy.isEmail(signId)) {
            return memberRepository.findByPrivacyEmail(signId).orElse(null);
        }

        Member member = null;
        if (Privacy.isPhoneNumber(signId)) {
            member = memberRepository.findByPrivacyPhoneNumber(signId).orElse(null);
        }

        return member != null ? member : memberRepository.findByNickname(signId).orElse(null);
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
