package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.controller.dto.CommentResponse;
import toyproject.instragram.controller.dto.CommonSliceResponse;
import toyproject.instragram.controller.dto.SliceInfo;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.service.CommentService;
import toyproject.instragram.service.ReplyService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ReplyService replyService;

    @GetMapping("/comments")
    public CommonSliceResponse<CommentResponse> getComments(@RequestParam Long postId, @RequestParam int page) {
        Slice<Comment> commentSlice = commentService.getCommentSlice(postId, page);

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
