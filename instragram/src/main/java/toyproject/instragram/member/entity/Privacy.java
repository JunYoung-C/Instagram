package toyproject.instragram.member.entity;

import lombok.*;

import javax.persistence.Embeddable;

import static toyproject.instragram.common.exception.ExceptionType.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Privacy {

    private String password;
    private String phoneNumber;
    private String email;

    public static Privacy create(String password, String phoneNumberOrEmail) {
        Privacy privacy = new Privacy();
        privacy.setPassword(password);
        privacy.setPhoneNumberOrEmail(phoneNumberOrEmail);

        return privacy;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setPhoneNumberOrEmail(String phoneNumberOrEmail) {
        if (isNumber(phoneNumberOrEmail)) {
            validatePhoneNumber(phoneNumberOrEmail);
            phoneNumber = phoneNumberOrEmail;
        } else {
            validateEmail(phoneNumberOrEmail);
            email = phoneNumberOrEmail;
        }
    }

    private boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!isPhoneNumber(phoneNumber)) {
            throw INVALID_PHONE_NUMBER.getException();
        }
    }

    public static boolean isPhoneNumber(String str) {
        return str.matches("\\d{9,11}");
    }

    private void validateEmail(String email) {
        if (!isEmail(email)) {
            throw INVALID_EMAIL.getException();
        }
    }

    public static boolean isEmail(String str) {
        return str.matches("^[A-Za-z\\d+_.-]+@(.+)$");
    }
}
