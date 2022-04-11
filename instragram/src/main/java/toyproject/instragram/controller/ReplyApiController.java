package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.controller.dto.CommentResponse;
import toyproject.instragram.controller.dto.CommonSliceResponse;
import toyproject.instragram.controller.dto.ReplyResponse;
import toyproject.instragram.controller.dto.SliceInfo;
import toyproject.instragram.entity.Reply;
import toyproject.instragram.service.ReplyService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @GetMapping("/replies")
    public CommonSliceResponse<ReplyResponse> getReplies(@RequestParam Long commentId, @RequestParam int page) {
        Slice<Reply> replySlice = replyService.getReplySlice(commentId, page);

        return new CommonSliceResponse(
                replySlice.getContent().stream().map(ReplyResponse::from).collect(Collectors.toList()),
                SliceInfo.from(replySlice));
    }

    @DeleteMapping("/replies/{replyId}")
    public void deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
    }
}
