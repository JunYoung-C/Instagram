package toyproject.instragram.post.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PostSaveForm {

    @NotNull
    private Long memberId;
    private List<MultipartFile> files;

    @NotBlank
    private String text;
}
