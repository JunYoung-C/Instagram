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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final int MAX_POST_SIZE = 10;

    @Transactional
    public Long addPost(PostDto postDto) {
        Member member = memberRepository.findById(postDto.getMemberId()).orElse(null);
        Post post = new Post(member, postDto.getText());
        postDto.getFileDtos().forEach(file -> post.addPostFile(
                new PostFile(post, file.getUploadFileName(), file.getStoreFileName(), file.getExtension())));

        postRepository.save(post);
        return post.getId();
    }

    public Slice<Post> getPostSlice(int page) {
        return postRepository.getPostsByOrderByCreatedDateDesc(PageRequest.of(page, MAX_POST_SIZE));
    }

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

