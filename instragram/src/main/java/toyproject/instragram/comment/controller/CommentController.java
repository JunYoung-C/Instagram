package toyproject.instragram.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.instragram.comment.service.CommentDto;
import toyproject.instragram.comment.service.CommentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public String addComment(@Valid CommentDto form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        commentService.addComment(new CommentDto(form.getPostId(), form.getMemberId(), form.getText()));
        return "redirect:/";
    }
}
