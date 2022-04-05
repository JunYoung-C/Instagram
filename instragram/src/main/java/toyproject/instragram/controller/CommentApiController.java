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

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ReplyService replyService;

    @GetMapping("/comments")
    public CommonSliceResponse getComments(@RequestParam Long postId, @RequestParam int page) {
        Slice<Comment> commentSlice = commentService.getCommentSlice(postId, page);
        List<Comment> contents = commentSlice.getContent();
        List<CommentResponse> commentResponses = new ArrayList<>();

        contents.stream().forEach(comment -> commentResponses.add(
                CommentResponse.from(comment, replyService.getReplyCount(comment.getId()))));

        return new CommonSliceResponse(commentResponses, SliceInfo.from(commentSlice));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
