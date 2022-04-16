package toyproject.instragram.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.repository.MemberProfileDto;
import toyproject.instragram.member.repository.MemberRepository;

import java.util.List;

import static toyproject.instragram.common.exception.form.FormExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        validateDuplication(memberDto.getNickname(), memberDto.getPhoneNumberOrEmail());

        Member member = new Member(
                Privacy.create(memberDto.getPassword(), memberDto.getPhoneNumberOrEmail()),
                memberDto.getNickname(),
                memberDto.getName());

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplication(String nickname, String phoneNumberOrEmail) {
        memberRepository.findByNickname(nickname).ifPresent(member1 -> {
            throw DUPLICATE_NICKNAME.getException();
        });

        if (Privacy.isEmail(phoneNumberOrEmail) &&
                memberRepository.findByPrivacyEmail(phoneNumberOrEmail).isPresent()) {
            throw DUPLICATE_EMAIL.getException();
        } else if (Privacy.isPhoneNumber(phoneNumberOrEmail) &&
                memberRepository.findByPrivacyPhoneNumber(phoneNumberOrEmail).isPresent()) {
            throw DUPLICATE_PHONE_NUMBER.getException();
        }
    }

    public Member signIn(String signInId, String password) {
        Member findMember = findBySignIdOrThrow(signInId);
        validatePassword(password, findMember);

        return findMember;
    }

    private Member findBySignIdOrThrow(String signId) {
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

    private void validatePassword(String password, Member findMember) {
        if (!findMember.isCorrectPassword(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
    }

    public List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable) {
        return memberRepository.searchProfiles(nickname, pageable);
    }
}
