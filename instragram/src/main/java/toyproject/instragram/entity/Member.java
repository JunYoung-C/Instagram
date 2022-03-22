package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    @Embedded
    private Profile profile;

    public Member(Privacy privacy, Profile profile) {
        this.privacy = privacy;
        this.profile = profile;
    }
}
