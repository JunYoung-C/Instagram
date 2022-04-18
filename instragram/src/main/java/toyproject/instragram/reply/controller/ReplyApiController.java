package toyproject.instragram.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.common.response.CommonSliceResponse;
import toyproject.instragram.common.response.SliceInfo;
import toyproject.instragram.reply.entity.Reply;
import toyproject.instragram.reply.controller.dto.ReplyResponse;
import toyproject.instragram.reply.service.ReplyService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @GetMapping("/replies")
    public CommonSliceResponse<List<ReplyResponse>> getReplies(
            @RequestParam(required = false) Long commentId, @PageableDefault(size = 20) Pageable pageable) {
        if (Objects.isNull(commentId)) {
            throw REQUIRED_COMMENT_ID.getException();
        }

        Slice<Reply> replySlice = replyService.getReplySlice(commentId, pageable);

        return new CommonSliceResponse<>(getReplyResponsesFrom(replySlice.getContent()), SliceInfo.from(replySlice));
    }

    private List<ReplyResponse> getReplyResponsesFrom(List<Reply> content) {
        return content.stream().map(ReplyResponse::from).collect(Collectors.toList());
    }

    @DeleteMapping("/replies/{replyId}")
    public void deleteReply(@SignIn SignInMember signInMember, @PathVariable(required = false) Long replyId) {
        if (Objects.isNull(replyId)) {
            throw REQUIRED_REPLY_ID.getException();
        }

        validateReplyAccess(signInMember.getMemberId(), getReplyOwnerId(replyId));
        replyService.deleteReply(replyId);
    }

    private void validateReplyAccess(Long signInMemberId, Long replyOwnerId) {
        if (!signInMemberId.equals(replyOwnerId)) {
            throw FORBIDDEN_REPLY.getException();
        }
    }

    private Long getReplyOwnerId(Long replyId) {
        return replyService.getReply(replyId).getMember().getId();
    }
}
