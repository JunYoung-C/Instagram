package toyproject.instragram.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.instragram.reply.controller.dto.ReplySaveForm;
import toyproject.instragram.reply.service.ReplyDto;
import toyproject.instragram.reply.service.ReplyService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/replies")
    public String addReply(@Valid ReplySaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        replyService.addReply(new ReplyDto(form.getCommentId(), form.getMemberId(), form.getText()));

        return "redirect:/";
    }
}
