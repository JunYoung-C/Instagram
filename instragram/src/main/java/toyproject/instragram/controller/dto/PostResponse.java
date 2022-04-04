package toyproject.instragram.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toyproject.instragram.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long postId;
    private InnerMemberResponse member;
    private List<String> filePaths;
    private String text;
    private LocalDateTime createdDate;
    private List<String> ownerComments;
    private Long commentCount;

    public static PostResponse from(Post post, List<String> ownerComments, Long commentCount) {
        return new PostResponse(
                post.getId(),
                new InnerMemberResponse(post.getMember()),
                getFilePaths(post),
                post.getText(),
                post.getCreatedDate(),
                ownerComments,
                commentCount);
    }

    private static List<String> getFilePaths(Post post) {
        return post.getPostFiles().stream().map(postFile -> postFile.getStoreFileName()).collect(Collectors.toList());
    }
}
