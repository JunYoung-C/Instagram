package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.instragram.controller.dto.MemberSaveForm;
import toyproject.instragram.service.MemberDto;
import toyproject.instragram.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/new")
    public String signUp(@Valid MemberSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        memberService.signUp(
                new MemberDto(form.getPhoneNumberOrEmail(), form.getName(), form.getNickname(), form.getPassword()));

        return "redirect:/sign-in";
    }

    @GetMapping("/members/new")
    public String signUpPage() {
        return "signUp";
    }
}
