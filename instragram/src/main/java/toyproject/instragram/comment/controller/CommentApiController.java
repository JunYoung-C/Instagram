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
import java.util.Objects;
import java.util.stream.Collectors;

import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ReplyService replyService;

    @GetMapping("/comments")
    public CommonSliceResponse<List<CommentResponse>> getComments(
            @RequestParam(required = false) Long postId, @PageableDefault(size = 20) Pageable pageable) {
        if (Objects.isNull(postId)) {
            throw REQUIRED_POST_ID.getException();
        }

        Slice<Comment> commentSlice = commentService.getCommentSlice(postId, pageable);
        return new CommonSliceResponse<>(getCommentResponsesFrom(commentSlice.getContent()), SliceInfo.from(commentSlice));
    }

    private List<CommentResponse> getCommentResponsesFrom(List<Comment> contents) {
        return contents.stream()
                .map(comment -> CommentResponse.of(comment, replyService.getReplyCount(comment.getId())))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@SignIn SignInMember signInMember, @PathVariable(required = false) Long commentId) {
        if (Objects.isNull(commentId)) {
            throw REQUIRED_COMMENT_ID.getException();
        }

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
