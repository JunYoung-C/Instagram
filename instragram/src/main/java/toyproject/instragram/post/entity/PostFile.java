package toyproject.instragram.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public PostFile(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    protected void setPost(Post post) {
        this.post = post;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}
