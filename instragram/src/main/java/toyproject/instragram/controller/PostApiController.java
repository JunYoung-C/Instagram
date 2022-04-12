package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.SignIn;
import toyproject.instragram.controller.dto.CommonSliceResponse;
import toyproject.instragram.controller.dto.PostResponse;
import toyproject.instragram.controller.dto.SliceInfo;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.service.CommentService;
import toyproject.instragram.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public CommonSliceResponse<List<PostResponse>> getPosts(@RequestParam int page) {
        Slice<Post> postSlice = postService.getPostSlice(page);

        return new CommonSliceResponse<>(getPostResponses(postSlice), SliceInfo.from(postSlice));
    }

    private List<PostResponse> getPostResponses(Slice<Post> postSlice) {
        return postSlice.getContent().stream()
                .map(post -> PostResponse.from(post, commentService.getCommentCount(post.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@SignIn Member signInMember, @PathVariable Long postId) {
        Post post = postService.getOwnerPost(postId, signInMember.getId()).orElse(null);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().body(PostResponse.from(post));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
