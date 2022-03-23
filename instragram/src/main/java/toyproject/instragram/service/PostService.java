package toyproject.instragram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.entity.Post;
import toyproject.instragram.repository.MemberRepository;
import toyproject.instragram.repository.PostRepository;
import toyproject.instragram.repository.dto.PostDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addPost(Post post) {
        // TODO controller 구현시 파라미터를 dto로 받고 변환하는 로직 필요
        postRepository.save(post);
        return post.getId();
    }

    public Slice<PostDto> getPosts(Pageable pageable) {
        return postRepository.getPostsByOrderByCreatedDateDesc(pageable);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
