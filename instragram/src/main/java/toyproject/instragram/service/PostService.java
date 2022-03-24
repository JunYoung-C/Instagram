package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Comment;
import toyproject.instragram.entity.Post;
import toyproject.instragram.repository.CommentRepository;
import toyproject.instragram.repository.PostRepository;
import toyproject.instragram.repository.dto.PostCommentDto;
import toyproject.instragram.repository.dto.PostDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final int MAX_COMMENT_COUNT = 3;

    @Transactional
    public Long addPost(Post post) {
        // TODO controller 구현시 파라미터를 dto로 받고 변환하는 로직 필요
        postRepository.save(post);
        return post.getId();
    }

    public Slice<PostDto> getPostDtoSlice(Pageable pageable) {
        Slice<Post> postSlice = postRepository.getPostsByOrderByCreatedDateDesc(pageable);
        return postSlice
                .map(post -> new PostDto(post, getTotalComments(post), getCommentDtoLists(getComments(post))));
    }

    private Long getTotalComments(Post post) {
        return commentRepository.countCommentsByPostId(post.getId());
    }

    private List<PostCommentDto> getCommentDtoLists(List<Comment> comments) {
        return comments
                .stream()
                .map(comment -> new PostCommentDto(comment.getId(), comment.getText()))
                .collect(Collectors.toList());
    }

    private List<Comment> getComments(Post post) {
        return commentRepository
                .getCommentsByMemberIdAndPostId(
                        post.getMember().getId(),
                        post.getId(),
                        PageRequest.of(0, MAX_COMMENT_COUNT))
                .getContent();
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}

