package toyproject.instragram.post.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toyproject.instragram.common.file.FileDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long memberId;
    private List<FileDto> fileDtos;
    private String text;
}
