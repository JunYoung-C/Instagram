package toyproject.instragram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/sign-in")
    public String signInPage() {
        return "signIn";
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "signUp";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}
