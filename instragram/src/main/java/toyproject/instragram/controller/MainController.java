package toyproject.instragram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import toyproject.instragram.SessionConst;
import toyproject.instragram.entity.Member;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String mainPage(
            @SessionAttribute(value = SessionConst.SIGN_IN_MEMBER, required = false) Member signInMember) {

        if (signInMember == null) {
            return "signIn";
        }

        return "main";
    }
}
