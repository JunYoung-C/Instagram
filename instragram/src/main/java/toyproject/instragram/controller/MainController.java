package toyproject.instragram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.instragram.SignIn;
import toyproject.instragram.controller.dto.SignInForm;
import toyproject.instragram.entity.Member;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(@SignIn Member signInMember, Model model) {

        if (signInMember == null) {
            model.addAttribute("signInForm", new SignInForm());
            return "signIn";
        }

        return "main";
    }
}
