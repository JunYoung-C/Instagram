package toyproject.instragram.entity;

import lombok.*;

import javax.persistence.Embeddable;

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
        if (phoneNumberOrEmail.contains("@")) {
            email = phoneNumberOrEmail;
        } else {
            phoneNumber = phoneNumberOrEmail;
        }
    }
}
