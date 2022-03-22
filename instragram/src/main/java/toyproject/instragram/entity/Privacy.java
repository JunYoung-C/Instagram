package toyproject.instragram.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Privacy {

    private String password;
    private String phoneNumber;
    private String email;
}
