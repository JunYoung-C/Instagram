package toyproject.instragram.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.instragram.common.exception.form.signup.DuplicateEmailException;
import toyproject.instragram.common.exception.form.signup.DuplicateNicknameException;
import toyproject.instragram.common.exception.form.signup.DuplicatePhoneNumberException;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.entity.Privacy;
import toyproject.instragram.member.repository.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Nested
    @DisplayName("회원가입")
    class signUp {
        @Test
        @DisplayName("성공")
        void success() {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            MemberDto memberDto =
                    new MemberDto("email@naver.com", "name", "nickname", "1234");

            //when
            //then
            assertThatNoException().isThrownBy(() -> memberService.signUp(memberDto));
        }

        @Test
        @DisplayName("닉네임 중복으로 인한 실패")
        void failByDuplicateNickname() {
            //given
            String nickname = "nickname";
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    nickname,
                    "name");

            when(memberRepository.findByNickname(nickname))
                    .thenReturn(Optional.of(member));

            MemberDto memberDto =
                    new MemberDto("email@naver.com", "name", "nickname", "1234");

            //when
            //then
            assertThatThrownBy(() -> memberService.signUp(memberDto))
                    .isExactlyInstanceOf(DuplicateNicknameException.class);
        }

        @Test
        @DisplayName("이메일 중복으로 인한 실패")
        void failByDuplicateEmail() {
            //given
            String email = "email@naver.com";
            Member member = new Member(
                    Privacy.create("1234", email),
                    "nickname",
                    "name");

            when(memberRepository.findByPrivacyEmail(email))
                    .thenReturn(Optional.of(member));

            MemberDto memberDto =
                    new MemberDto(email, "name", "nickname", "1234");

            //when
            //then
            assertThatThrownBy(() -> memberService.signUp(memberDto))
                    .isExactlyInstanceOf(DuplicateEmailException.class);
        }

        @Test
        @DisplayName("휴대폰 번호 중복으로 인한 실패")
        void failByDuplicatePhoneNumber() {
            //given
            String phoneNumber = "01011111111";
            Member member = new Member(
                    Privacy.create("1234", phoneNumber),
                    "nickname",
                    "name");

            when(memberRepository.findByPrivacyPhoneNumber(phoneNumber))
                    .thenReturn(Optional.of(member));

            MemberDto memberDto =
                    new MemberDto(phoneNumber, "name", "nickname", "1234");
            
            //when
            //then
            assertThatThrownBy(() -> memberService.signUp(memberDto))
                    .isExactlyInstanceOf(DuplicatePhoneNumberException.class);
        }
    }



    @Test
    void signIn() {
    }

    @Test
    void searchProfiles() {
    }
}