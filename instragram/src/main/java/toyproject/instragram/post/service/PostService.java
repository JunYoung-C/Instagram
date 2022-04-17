package toyproject.instragram.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.instragram.common.file.FileManager;
import toyproject.instragram.member.entity.Member;
import toyproject.instragram.member.repository.MemberRepository;
import toyproject.instragram.post.entity.Post;
import toyproject.instragram.post.entity.PostFile;
import toyproject.instragram.post.repository.PostRepository;

import static toyproject.instragram.common.exception.api.ApiExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;

    @Transactional
    public Long addPost(PostDto postDto) {
        Member member = memberRepository.findById(postDto.getMemberId())
                .orElseThrow(NOT_FOUND_MEMBER::getException);
        Post post = new Post(member, postDto.getText());
        postDto.getFileDtos().forEach(file -> post.addPostFile(
                new PostFile(post, file.getUploadFileName(), file.getStoreFileName(), file.getExtension())));

        postRepository.save(post);
        return post.getId();
    }

    public Slice<Post> getPostSlice(Pageable pageable) {
        return postRepository.getPostsByOrderByCreatedDateDesc(pageable);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(NOT_FOUND_POST::getException);
    }

    @Transactional
    public void modifyPostText(Long postId, String modifiedText) {
        Post post = postRepository.findById(postId).orElseThrow(NOT_FOUND_POST::getException);
        post.changeText(modifiedText);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.findById(postId).ifPresent(this::deletePostFiles);
        postRepository.deleteById(postId);
    }

    private void deletePostFiles(Post post) {
        post.getPostFiles().forEach((postFile) ->
                fileManager.deleteFile(postFile.getStoreFileName(), postFile.getExtension()));
    }
}

