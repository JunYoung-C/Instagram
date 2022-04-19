package toyproject.instragram.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.comment.controller.dto.CommentSaveForm;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.common.exception.form.CustomFormException;
import toyproject.instragram.common.file.FileDto;
import toyproject.instragram.common.file.FileManager;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.post.controller.dto.PostUpdateForm;
import toyproject.instragram.post.service.PostDto;
import toyproject.instragram.post.service.PostService;
import toyproject.instragram.reply.controller.dto.ReplySaveForm;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final FileManager fileManager;

    @PostMapping("/posts")
    public String addPost(@SignIn SignInMember signInMember, @Valid PostSaveForm form,
                          BindingResult bindingResult, Model model) throws IOException {
        try {
            if (!bindingResult.hasFieldErrors()) {
                List<FileDto> fileDtos = fileManager.storeImageFiles(form.getFiles());
                postService.addPost(new PostDto(signInMember.getMemberId(), fileDtos, form.getText()));
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("postUpdateForm", new PostUpdateForm());
            model.addAttribute("commentSaveForm", new CommentSaveForm());
            model.addAttribute("replySaveForm", new ReplySaveForm());
            model.addAttribute("signInMember", signInMember);
            return "main";
        }

        return "redirect:/";
    }

    @PostMapping("/posts/{postId}")
    public String modifyPost(@SignIn SignInMember signInMember, @PathVariable Long postId,
                             @Valid PostUpdateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postSaveForm", new PostSaveForm());
            model.addAttribute("commentSaveForm", new CommentSaveForm());
            model.addAttribute("replySaveForm", new ReplySaveForm());
            model.addAttribute("signInMember", signInMember);
            return "main";
        }

        postService.modifyPostText(postId, form.getModifiedText());
        return "redirect:/";
    }

}
