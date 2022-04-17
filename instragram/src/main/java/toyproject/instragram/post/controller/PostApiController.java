package toyproject.instragram.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.common.auth.SignIn;
import toyproject.instragram.common.auth.SignInMember;
import toyproject.instragram.common.response.CommonSliceResponse;
import toyproject.instragram.common.response.SliceInfo;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.controller.dto.PostResponse;
import toyproject.instragram.comment.service.CommentService;
import toyproject.instragram.post.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public CommonSliceResponse<List<PostResponse>> getPosts(@PageableDefault Pageable pageable) {
        Slice<Post> postSlice = postService.getPostSlice(pageable);

        return new CommonSliceResponse<>(getPostResponses(postSlice), SliceInfo.from(postSlice));
    }

    private List<PostResponse> getPostResponses(Slice<Post> postSlice) {
        return postSlice.getContent().stream()
                .map(post -> PostResponse.from(post, commentService.getCommentCount(post.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@SignIn SignInMember signInMember, @PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.getOwnerPostByPostId(postId, signInMember.getMemberId())));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
