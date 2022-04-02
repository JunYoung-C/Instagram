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

    @Column(unique = true)
    private String nickname;
    private String name;

    @Embedded
    private Privacy privacy;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImage memberImage;

    public Member(String nickname, String name, Privacy privacy, MemberImage memberImage) {
        this.nickname = nickname;
        this.name = name;
        this.privacy = privacy;
        this.memberImage = memberImage;
    }
}
