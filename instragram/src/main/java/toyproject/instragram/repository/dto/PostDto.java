package toyproject.instragram.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import toyproject.instragram.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto {

    @JsonIgnore
    private Long postId;
    private PostMemberDto memberDto;
    private String photoPath;
    private LocalDateTime createdDate;
    private Long totalComments;
    private List<PostCommentDto> myCommentDtoList;

    public PostDto(Post post, Long totalComments, List<PostCommentDto> myCommentDtoList) {
        this.postId = post.getId();
        this.memberDto = new PostMemberDto(post.getMember().getId(),
                post.getMember().getProfile().getNickname(),
                post.getMember().getProfile().getPhotoPath());
        this.photoPath = post.getPhotoPath();
        this.createdDate = post.getCreatedDate();
        this.totalComments = totalComments;
        this.myCommentDtoList = myCommentDtoList;
    }
}
