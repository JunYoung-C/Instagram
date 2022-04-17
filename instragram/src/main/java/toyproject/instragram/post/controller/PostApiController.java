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

import static toyproject.instragram.common.exception.api.ApiExceptionType.FORBIDDEN_POST;

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
        Post findPost = postService.getPost(postId);
        validateAccess(signInMember.getMemberId(), findPost.getMember().getId());

        return ResponseEntity.ok()
                .body(PostResponse.from(findPost));
    }

    private void validateAccess(Long signInMemberId, Long PostOwnerId) {
        if (!signInMemberId.equals(PostOwnerId)) {
            throw FORBIDDEN_POST.getException();
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@SignIn SignInMember signInMember, @PathVariable Long postId) {
        validateAccess(signInMember.getMemberId(), getPostOwnerId(postId));
        postService.deletePost(postId);
    }

    private Long getPostOwnerId(Long postId) {
        return postService.getPost(postId).getMember().getId();
    }
}
