package toyproject.instragram.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import toyproject.instragram.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    @JsonIgnore
    private Long postId;
    private MemberDto memberDto;
    private String photoPath;
    private LocalDateTime createdDate;
    private Long commentCount;
    private List<PostCommentDto> myCommentDtoList;

    public PostDto(Post post, Long commentCount, List<PostCommentDto> myCommentDtoList) {
        this.postId = post.getId();
        this.memberDto = new MemberDto(post.getMember().getId(),
                post.getMember().getProfile().getNickname(),
                post.getMember().getProfile().getPhotoPath());
        this.photoPath = post.getPhotoPath();
        this.createdDate = post.getCreatedDate();
        this.commentCount = commentCount;
        this.myCommentDtoList = myCommentDtoList;
    }
}
