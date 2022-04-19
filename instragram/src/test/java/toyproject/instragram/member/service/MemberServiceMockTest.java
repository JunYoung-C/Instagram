package toyproject.instragram.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.instragram.common.exception.form.signin.IncorrectPasswordException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByEmailException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByNicknameException;
import toyproject.instragram.common.exception.form.signin.NotFoundAccountByPhoneNumberException;
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
        @DisplayName("성공")
        @Test
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

        @DisplayName("닉네임 중복으로 인한 실패")
        @Test
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

        @DisplayName("이메일 중복으로 인한 실패")
        @Test
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


    @Nested
    @DisplayName("로그인")
    class signIn {
        @DisplayName("성공")
        @ParameterizedTest
        @CsvSource({
                "nickname, 1234",
                "01011111111, 1234",
                "email@naver.com, 1234"
        })
        void success(String signInId, String password) {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            lenient().when(memberRepository.findByPrivacyEmail("email@naver.com"))
                    .thenReturn(Optional.of(member));
            lenient().when(memberRepository.findByPrivacyPhoneNumber("01011111111"))
                    .thenReturn(Optional.of(member));
            lenient().when(memberRepository.findByNickname("nickname"))
                    .thenReturn(Optional.of(member));

            //when
            //then
            assertThatNoException().isThrownBy(() -> memberService.signIn(signInId, password));
        }

        @DisplayName("아이디 틀림")
        @ParameterizedTest
        @CsvSource({
                "nickname1, 1234",
                "010111111112, 1234",
                "email1@naver.com, 1234",
        })
        void failById(String signInId, String password) {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            //when
            //then
            assertThatThrownBy(() -> memberService.signIn(signInId, password))
                    .isInstanceOfAny(NotFoundAccountByEmailException.class,
                            NotFoundAccountByNicknameException.class,
                            NotFoundAccountByPhoneNumberException.class);
        }

        @DisplayName("비밀번호 틀림")
        @ParameterizedTest
        @CsvSource({
                "nickname, 1111",
                "01011111111, 1111",
                "email@naver.com, 1111"
        })
        void failByPassword(String signInId, String password) {
            //given
            Member member = new Member(
                    Privacy.create("1234", "01011111111"),
                    "nickname",
                    "name");

            lenient().when(memberRepository.findByPrivacyEmail("email@naver.com"))
                    .thenReturn(Optional.of(member));
            lenient().when(memberRepository.findByPrivacyPhoneNumber("01011111111"))
                    .thenReturn(Optional.of(member));
            lenient().when(memberRepository.findByNickname("nickname"))
                    .thenReturn(Optional.of(member));

            //when
            //then
            assertThatThrownBy(() -> memberService.signIn(signInId, password))
                    .isExactlyInstanceOf(IncorrectPasswordException.class);
        }
    }

    @Test
    void searchProfiles() {
    }
}