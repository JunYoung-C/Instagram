package toyproject.instragram.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.instragram.common.entity.BaseEntity;

import javax.persistence.*;

import static toyproject.instragram.common.exception.ExceptionType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "MEMBER_UK_NICKNAME", columnNames = {"nickname"}),
        @UniqueConstraint(name = "MEMBER_UK_PHONE_NUMBER", columnNames = {"phoneNumber"}),
        @UniqueConstraint(name = "MEMBER_UK_EMAIL", columnNames = {"email"})})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Privacy privacy;
    private String nickname;
    private String name;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImage memberImage;

    public Member(Privacy privacy, String nickname, String name) {
        validateNickname(nickname);
        this.privacy = privacy;
        this.nickname = nickname;
        this.name = name;
        this.memberImage = MemberImage.createBasicImage();
    }

    private void validateNickname(String nickname) {
        if (isNumber(nickname)) {
            throw INVALID_NICKNAME_BY_NUMBER.getException();
        } else if (!isValidFormat(nickname)) {
            throw INVALID_NICKNAME_BY_STRING.getException();
        }
    }

    private boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    private boolean isValidFormat(String nickname) {
        return nickname.matches("^[a-zA-Z가-힣\\d._]+$");
    }

    public void changeProfileImage(MemberImage memberImage) {
        this.memberImage = memberImage;
        memberImage.setMember(this);
    }

    public boolean isCorrectPassword(String password) {
        return privacy.getPassword().equals(password);
    }
}
