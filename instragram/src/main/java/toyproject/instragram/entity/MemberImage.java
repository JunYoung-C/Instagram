package toyproject.instragram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImage {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public MemberImage(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    protected void setMember(Member member) {
        this.member = member;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}
