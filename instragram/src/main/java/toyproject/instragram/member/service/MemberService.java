package toyproject.instragram.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.common.exception.ExceptionType;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.MemberImage;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.repository.MemberProfileDto;
import toyproject.instragram.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static toyproject.instragram.common.exception.ExceptionType.*;

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

        if (!findMember.isCorrectPassword(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
        return findMember;
    }

    private Member findBySignId(String signId) {
        if (Privacy.isEmail(signId)) {
            return memberRepository.findByPrivacyEmail(signId)
                    .orElseThrow(NOT_FOUND_ACCOUNT_BY_Email::getException);
        } else if (Privacy.isPhoneNumber(signId)) {
            return memberRepository.findByPrivacyPhoneNumber(signId)
                    .orElseThrow(NOT_FOUND_ACCOUNT_BY_Phone_Number::getException);
        } else {
            return memberRepository.findByNickname(signId)
                    .orElseThrow(NOT_FOUND_ACCOUNT_BY_NICKNAME::getException);
        }
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
