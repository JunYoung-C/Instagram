package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.controller.dto.PostSaveForm;
import toyproject.instragram.service.PostDto;
import toyproject.instragram.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO html 보완 후 테스트
    @PostMapping("/posts")
    public String addPost(@Valid PostSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        postService.addPost(new PostDto(form.getMemberId(), getFilePaths(form), form.getText()));
        return "redirect:/";
    }

    // TODO html 보완 후 테스트
    @PostMapping("/posts/{postId}")
    public String modifyPost(@PathVariable Long postId, @RequestParam String modifiedText) {
        postService.modifyPostText(postId, modifiedText);
        return "redirect:/main";
    }

    private List<String> getFilePaths(PostSaveForm form) {
        return form.getFiles().stream().map(file -> file.getOriginalFilename()).collect(Collectors.toList());
    }
}
