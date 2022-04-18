package toyproject.instragram.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.comment.controller.dto.CommentResponse;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.common.response.CommonSliceResponse;
import toyproject.instragram.common.response.SliceInfo;
import toyproject.instragram.comment.entity.Comment;
import toyproject.instragram.comment.service.CommentService;
import toyproject.instragram.reply.service.ReplyService;

import java.util.List;
import java.util.stream.Collectors;

import static toyproject.instragram.common.exception.api.ApiExceptionType.FORBIDDEN_COMMENT;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ReplyService replyService;

    @GetMapping("/comments")
    public CommonSliceResponse<List<CommentResponse>> getComments(
            @RequestParam Long postId, @PageableDefault(size = 20) Pageable pageable) {
        Slice<Comment> commentSlice = commentService.getCommentSlice(postId, pageable);

        return new CommonSliceResponse<>(getCommentResponses(commentSlice.getContent()), SliceInfo.from(commentSlice));
    }

    private List<CommentResponse> getCommentResponses(List<Comment> contents) {
        return contents.stream()
                .map(comment -> CommentResponse.from(comment, replyService.getReplyCount(comment.getId())))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@SignIn SignInMember signInMember, @PathVariable Long commentId) {
        validateCommentAccess(signInMember.getMemberId(), getCommentOwnerId(commentId));
        commentService.deleteComment(commentId);
    }

    private void validateCommentAccess(Long signInMemberId, Long commentOwnerId) {
        if (!signInMemberId.equals(commentOwnerId)) {
            throw FORBIDDEN_COMMENT.getException();
        }
    }

    private Long getCommentOwnerId(Long commentId) {
        return commentService.getComment(commentId).getMember().getId();
    }

}
