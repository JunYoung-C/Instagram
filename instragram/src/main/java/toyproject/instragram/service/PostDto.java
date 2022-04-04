package toyproject.instragram.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long memberId;
    private List<String> filePaths;
    private String text;
}
