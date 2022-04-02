package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Privacy privacy;

    @Column(unique = true)
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
