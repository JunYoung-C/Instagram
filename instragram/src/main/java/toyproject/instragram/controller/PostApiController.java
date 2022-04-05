package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.controller.dto.CommonSliceResponse;
import toyproject.instragram.controller.dto.PostResponse;
import toyproject.instragram.controller.dto.SliceInfo;
import toyproject.instragram.entity.Post;
import toyproject.instragram.service.CommentService;
import toyproject.instragram.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/posts")
    public CommonSliceResponse<PostResponse> getPosts(@RequestParam int page) {
        Slice<Post> postSlice = postService.getPostSlice(page);

        return new CommonSliceResponse(getPostResponses(postSlice), SliceInfo.from(postSlice));
    }

    private List<PostResponse> getPostResponses(Slice<Post> postSlice) {
        return postSlice.getContent().stream()
                .map(post -> PostResponse.from(post,
                        getOwnerCommentTextList(post),
                        commentService.getCommentCount(post.getId())))
                .collect(Collectors.toList());
    }

    private List<String> getOwnerCommentTextList(Post post) {
        return commentService.getOwnerComments(post.getMember().getId(), post.getId())
                .stream().map(comment -> comment.getText()).collect(Collectors.toList());
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
