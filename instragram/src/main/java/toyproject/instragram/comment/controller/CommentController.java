package toyproject.instragram.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.instragram.comment.controller.dto.CommentSaveForm;
import toyproject.instragram.comment.service.CommentDto;
import toyproject.instragram.comment.service.CommentService;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.post.controller.dto.PostUpdateForm;
import toyproject.instragram.reply.controller.dto.ReplySaveForm;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public String addComment(@SignIn SignInMember signInMember, @Valid CommentSaveForm form,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postSaveForm", new PostSaveForm());
            model.addAttribute("postUpdateForm", new PostUpdateForm());
            model.addAttribute("replySaveForm", new ReplySaveForm());
            model.addAttribute("signInMember", signInMember);
            return "main";
        }

        commentService.addComment(new CommentDto(form.getPostId(), signInMember.getMemberId(), form.getText()));
        return "redirect:/";
    }
}
