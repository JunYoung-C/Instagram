package toyproject.instragram.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Profile {

    @Column(unique = true)
    private String nickname;
    private String name;
    private String photoPath;
}
