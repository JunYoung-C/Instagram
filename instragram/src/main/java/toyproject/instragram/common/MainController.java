package toyproject.instragram.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.comment.controller.dto.CommentSaveForm;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.member.controller.dto.SignInForm;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(@SignIn SignInMember signInMember, Model model) {

        if (signInMember == null) {
            model.addAttribute("signInForm", new SignInForm());
            return "signIn";
        }

        CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setMemberId(signInMember.getMemberId());

        model.addAttribute("postSaveForm", new PostSaveForm());
        model.addAttribute("commentSaveForm", commentSaveForm);
        model.addAttribute("signInMember", signInMember);
        return "main";
    }
}
