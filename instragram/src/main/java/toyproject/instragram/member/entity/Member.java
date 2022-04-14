package toyproject.instragram.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.instragram.common.entity.BaseEntity;

import javax.persistence.*;

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
    private String nickname; //TODO 회원 별명은 문자, 숫자, 밑줄 및 마침표만 사용 가능. 검증 필요
    private String name;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImage memberImage;

    public Member(Privacy privacy, String nickname, String name) {
        this.privacy = privacy;
        this.nickname = nickname;
        this.name = name;
    }

    public void addProfileImage(MemberImage memberImage) {
        this.memberImage = memberImage;
        memberImage.setMember(this);
    }

    public boolean isCorrectPassword(String password) {
        return privacy.getPassword().equals(password);
    }
}
