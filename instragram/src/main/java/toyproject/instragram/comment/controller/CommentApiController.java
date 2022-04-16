package toyproject.instragram.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.comment.controller.dto.CommentResponse;
import toyproject.instragram.common.response.CommonSliceResponse;
import toyproject.instragram.common.response.SliceInfo;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.comment.service.CommentService;
import toyproject.instragram.reply.service.ReplyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ReplyService replyService;

    @GetMapping("/comments")
    public CommonSliceResponse<CommentResponse> getComments(@RequestParam Long postId, Pageable pageable) {
        Slice<Comment> commentSlice = commentService.getCommentSlice(postId, pageable);

        return new CommonSliceResponse(getCommentResponses(commentSlice.getContent()), SliceInfo.from(commentSlice));
    }

    private List<CommentResponse> getCommentResponses(List<Comment> contents) {
        return contents.stream()
                .map(comment -> CommentResponse.from(comment, replyService.getReplyCount(comment.getId())))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
