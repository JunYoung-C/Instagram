package toyproject.instragram.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toyproject.instragram.common.exception.form.CustomFormException;
import toyproject.instragram.common.exception.form.EmptyFileException;
import toyproject.instragram.common.exception.form.FormExceptionType;
import toyproject.instragram.common.file.FileDto;
import toyproject.instragram.common.file.FileManager;
import toyproject.instragram.post.controller.dto.PostSaveForm;
import toyproject.instragram.post.service.PostDto;
import toyproject.instragram.post.service.PostService;

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
    public String addPost(@Valid PostSaveForm form, BindingResult bindingResult, Model model) throws IOException {

        try {
            List<FileDto> fileDtos = fileManager.storeFiles(form.getFiles());
            postService.addPost(new PostDto(form.getMemberId(), fileDtos, form.getText()));
        } catch (CustomFormException e) {
            System.out.println("e.getField() = " + e.getField());
            bindingResult.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "main";
        }


        return "redirect:/";
    }

    @PostMapping("/posts/{postId}")
    public String modifyPost(@PathVariable Long postId, @RequestParam String modifiedText) {
        postService.modifyPostText(postId, modifiedText);
        return "redirect:/";
    }

}
