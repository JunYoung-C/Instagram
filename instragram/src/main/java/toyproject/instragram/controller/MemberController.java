package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.instragram.SessionConst;
import toyproject.instragram.controller.dto.MemberSaveForm;
import toyproject.instragram.controller.dto.SignInForm;
import toyproject.instragram.entity.Member;
import toyproject.instragram.service.MemberDto;
import toyproject.instragram.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/signup")
    public String signUpPage() {
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        memberService.signUp(new MemberDto(form.getPhoneNumberOrEmail(), form.getName(), form.getNickname(), form.getPassword()));

        return "redirect:/sign-in";
    }

    @GetMapping("/signin")
    public String signInPage() {
        return "signIn";
    }

    @PostMapping("/signin")
    public String signIn(@Valid SignInForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "signIn";
        }

        Member signInMember = memberService.signIn(form.getSignInId(), form.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SIGN_IN_MEMBER, signInMember);

        return "redirect:/";
    }
}
