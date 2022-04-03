package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Member;
import toyproject.instragram.entity.Post;
import toyproject.instragram.entity.PostFile;
import toyproject.instragram.repository.MemberRepository;
import toyproject.instragram.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileManager;

    private final int MAX_POST_SIZE = 10;

    @Transactional
    public Long addPost(Long memberId, List<String> filePaths, String text) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = new Post(member, text);
        filePaths.stream().forEach(filePath -> {
                    PostFile postFile = new PostFile(post,
                            fileManager.extractFileName(filePath),
                            fileManager.createStoreFileName(),
                            fileManager.extractExtension(filePath));

                    post.addPostFile(postFile);
                }
        );

        postRepository.save(post);
        return post.getId();
    }

    public Slice<Post> getPostSlice(int page) {
        return postRepository.getPostsByOrderByCreatedDateDesc(PageRequest.of(page, MAX_POST_SIZE));
    }

//    private Long getCommentCount(Post post) {
//        return commentRepository.countCommentsByPostId(post.getId());
//    }

//    private List<PostCommentDto> getCommentDtoLists(List<Comment> comments) {
//        return comments
//                .stream()
//                .map(comment -> new PostCommentDto(comment.getId(), comment.getText()))
//                .collect(Collectors.toList());
//    }

//    private List<Comment> getComments(Post post) {
//        return commentRepository
//                .getCommentsByMemberIdAndPostId(
//                        post.getMember().getId(),
//                        post.getId(),
//                        PageRequest.of(0, MAX_COMMENT_COUNT))
//                .getContent();
//    }

    @Transactional
    public void modifyPostText(Long postId, String modifiedText) {
        Post post = postRepository.findById(postId).orElse(null);
        post.changeText(modifiedText);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}

