package toyproject.instragram.post.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PostSaveForm {

    private List<MultipartFile> files;

    @Size(max = 2200, message = "글자 길이는 {0}자를 넘을 수 없습니다.")
    private String text;
}
