package toyproject.instragram.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long postId;
    private Long memberId;
    private String text;
}
