package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String nickname;
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
}
