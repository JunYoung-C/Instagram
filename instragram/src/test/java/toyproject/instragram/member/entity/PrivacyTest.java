package toyproject.instragram.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import toyproject.instragram.common.exception.form.signup.InvalidEmailException;
import toyproject.instragram.common.exception.form.signup.InvalidPhoneNumberException;

import static org.assertj.core.api.Assertions.*;

class PrivacyTest {

    @Nested
    @DisplayName("휴대폰 번호가 맞는지 확인")
    class isPhoneNumberTest {

        @DisplayName("성공")
        @ParameterizedTest
        @ValueSource(strings = {"010123456", "0101234567", "01012345678"})
        void success(String phoneNumber) {
            //given
            //when
            boolean isPhoneNumber = Privacy.isPhoneNumber(phoneNumber);

            //then
            assertThat(isPhoneNumber).isTrue();
        }

        @DisplayName("실패")
        @ParameterizedTest
        @ValueSource(strings = {"12345678", "123456789999"})
        void fail(String phoneNumber) {
            //given
            //when
            boolean isPhoneNumber = Privacy.isPhoneNumber(phoneNumber);

            //then
            assertThat(isPhoneNumber).isFalse();
        }
    }

    @Nested
    @DisplayName("이메일이 맞는지 확인")
    class isEmailTest {

        @DisplayName("성공")
        @ParameterizedTest
        @ValueSource(strings = {"1@1", "JUN_young.c-123@naver.com"})
        void success(String email) {
            //given
            //when
            boolean isEmail = Privacy.isEmail(email);

            //then
            assertThat(isEmail).isTrue();
        }

        @DisplayName("실패")
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {"@1", "1@", " @1", " ", "@", "한글@1", "$@1", "+@1", "@@1", ")@1"})
        void fail(String email) {
            //given
            //when
            boolean isEmail = Privacy.isEmail(email);

            //then
            assertThat(isEmail).isFalse();
        }
    }

    @Nested
    @DisplayName("정적 팩토리 메소드 검증")
    class createTest {
        @DisplayName("성공")
        @ParameterizedTest
        @CsvSource({
                "JUN_young.c-123@naver.com, 1234",
                "01012345678, 1234"
        })
        void success(String phoneNumberOrEmail, String password) {
            //given
            //when
            //then
            assertThatNoException().isThrownBy(() -> Privacy.create(password, phoneNumberOrEmail));
        }

        @DisplayName("실패 - 유효하지 않은 휴대폰 번호")
        @ParameterizedTest
        @CsvSource({
                "12345678, 1234",
                "123456789999, 1234",
        })
        void failByPhoneNumber(String phoneNumber, String password) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Privacy.create(password, phoneNumber))
                    .isExactlyInstanceOf(InvalidPhoneNumberException.class);
        }

        @DisplayName("실패 - 유효하지 않은 이메일")
        @ParameterizedTest
        @CsvSource({
                "1@, 1234",
                "한글@naver.com, 1234",
        })
        void failByEmail(String email, String password) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Privacy.create(password, email))
                    .isExactlyInstanceOf(InvalidEmailException.class);
        }
    }
}