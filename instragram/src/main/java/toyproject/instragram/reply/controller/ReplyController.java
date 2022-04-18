package toyproject.instragram.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.instragram.comment.controller.dto.CommentSaveForm;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.post.controller.dto.PostUpdateForm;
import toyproject.instragram.reply.controller.dto.ReplySaveForm;
import toyproject.instragram.reply.service.ReplyDto;
import toyproject.instragram.reply.service.ReplyService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/replies")
    public String addReply(@SignIn SignInMember signInMember,
                           @Valid ReplySaveForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postSaveForm", new PostSaveForm());
            model.addAttribute("postUpdateForm", new PostUpdateForm());
            model.addAttribute("commentSaveForm", new CommentSaveForm());
            model.addAttribute("signInMember", signInMember);
            return "main";
        }

        replyService.addReply(new ReplyDto(form.getCommentId(), signInMember.getMemberId(), form.getText()));

        return "redirect:/";
    }
}
