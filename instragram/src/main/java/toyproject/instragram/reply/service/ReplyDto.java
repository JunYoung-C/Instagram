package toyproject.instragram.reply.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyDto {
    private Long commentId;
    private Long memberId;
    private String text;
}
