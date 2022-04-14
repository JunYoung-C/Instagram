package toyproject.instragram.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.common.file.FileManager;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.post.service.PostDto;
import toyproject.instragram.post.service.PostService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final FileManager fileManager;

    @PostMapping("/posts")
    public String addPost(@Valid PostSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        postService.addPost(new PostDto(form.getMemberId(), fileManager.storeFiles(form.getFiles()), form.getText()));
        return "redirect:/";
    }

    @PostMapping("/posts/{postId}")
    public String modifyPost(@PathVariable Long postId, @RequestParam String modifiedText) {
        postService.modifyPostText(postId, modifiedText);
        return "redirect:/";
    }

}
