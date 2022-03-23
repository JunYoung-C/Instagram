package toyproject.instragram.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostCommentDto {
    private Long commentId;
    private String text;

    @QueryProjection
    public PostCommentDto(Long commentId, String text) {
        this.commentId = commentId;
        this.text = text;
    }
}
