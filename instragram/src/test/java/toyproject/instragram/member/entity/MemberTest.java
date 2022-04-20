package toyproject.instragram.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import toyproject.instragram.common.exception.form.signup.InvalidNicknameByNumberException;
import toyproject.instragram.common.exception.form.signup.InvalidNicknameByStringException;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Nested
    @DisplayName("생성자 검증")
    class MemberConstructorTest {
        @DisplayName("성공")
        @ParameterizedTest
        @ValueSource(strings = {"nickname", "Nick_네임.123"})
        void success(String nickname) {
            //given
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> new Member(null, nickname, "name"));
        }

        @DisplayName("실패 - 유효하지 않은 숫자 닉네임")
        @ParameterizedTest
        @ValueSource(strings = {"01012345678", "1"})
        void failByNumberNickname(String nickname) {
            //given
            //when
            //then
            assertThatThrownBy(() -> new Member(null, nickname, "name"))
                    .isExactlyInstanceOf(InvalidNicknameByNumberException.class);
        }

        @DisplayName("실패 - 유효하지 않은 닉네임 포맷")
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "#", "%", "(", "email@naver.com", "="})
        void failByNicknameFormat(String nickname) {
            //given
            //when
            //then
            assertThatThrownBy(() -> new Member(null, nickname, "name"))
                    .isExactlyInstanceOf(InvalidNicknameByStringException.class);
        }
    }

    @DisplayName("게시물 프로필 사진 변경")
    @Test
    void changeProfileImage() {
        //given
        Member member = new Member(null, "nickname", "name");
        MemberImage memberImage = new MemberImage("upload", "store", "png");

        //when
        member.changeProfileImage(memberImage);

        //then
        assertThat(member.getMemberImage()).isEqualTo(memberImage);
        assertThat(memberImage.getMember()).isEqualTo(member);
    }

    @DisplayName("비밀번호 확인")
    @Test
    void isCorrectPassword() {
        //given
        String password = "1234";
        Member member = new Member(
                Privacy.create(password, "email@naver.com"),
                "nickname", "name");

        //when
        boolean isCorrectPassword1 = member.isCorrectPassword(password);
        boolean isCorrectPassword2 = member.isCorrectPassword("1111");

        //then
        assertThat(isCorrectPassword1).isTrue();
        assertThat(isCorrectPassword2).isFalse();
    }
}