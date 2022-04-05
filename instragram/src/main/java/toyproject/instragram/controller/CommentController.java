package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.instragram.controller.dto.PostSaveForm;
import toyproject.instragram.service.CommentDto;
import toyproject.instragram.service.CommentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // TODO html 보완 후 테스트
    @PostMapping("/comments")
    public String addComment(@Valid PostSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        commentService.addComment(new CommentDto(form.getMemberId(), form.getMemberId(), form.getText()));
        return "redirect:/";
    }

    // TODO html 보완 후 테스트
    @PostMapping("/comments/{commentId}")
    public String modifyComment(@PathVariable Long commentId, @RequestParam String modifiedText) {
        commentService.modifyCommentText(commentId, modifiedText);
        return "redirect:/";
    }
}
