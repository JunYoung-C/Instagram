package toyproject.instragram.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.instragram.common.auth.SessionConst;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.common.exception.inheritance.IncorrectPasswordException;
import toyproject.instragram.common.exception.inheritance.notfoundaccount.NotFoundAccountException;
import toyproject.instragram.common.exception.inheritance.notfoundaccount.UnknownException;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.controller.dto.MemberSaveForm;
import toyproject.instragram.member.controller.dto.SignInForm;
import toyproject.instragram.member.service.MemberDto;
import toyproject.instragram.member.service.MemberService;

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
    public String signUpPage(MemberSaveForm form) {
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        memberService.signUp(new MemberDto(form.getPhoneNumberOrEmail(), form.getName(), form.getNickname(), form.getPassword()));

        return "redirect:/members/signin";
    }

    @GetMapping("/signin")
    public String signInPage(SignInForm form) {
        return "signIn";
    }

    @PostMapping("/signin")
    public String signIn(@Valid SignInForm form, BindingResult bindingResult, HttpServletRequest request) {

        Member findMember = null;
        try {
            findMember = memberService.signIn(form.getSignInId(), form.getPassword());
        } catch (NotFoundAccountException e) {
            bindingResult.rejectValue("signInId", "incorrect", e.getMessage());
        } catch (IncorrectPasswordException e) {
            bindingResult.rejectValue("password", "incorrect", e.getMessage());
        } catch (UnknownException e) {
            bindingResult.reject("unknown", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            return "signIn";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SIGN_IN_MEMBER, SignInMember.from(findMember));

        return "redirect:/";
    }
}
