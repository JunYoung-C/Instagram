package toyproject.instragram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.instragram.SignIn;
import toyproject.instragram.entity.Member;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(@SignIn Member signInMember) {

        if (signInMember == null) {
            return "signIn";
        }

        return "main";
    }
}
