package toyproject.instragram.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import toyproject.instragram.entity.Post;
import toyproject.instragram.repository.dto.PostCommentDto;
import toyproject.instragram.repository.dto.PostDto;

import java.util.List;

public interface PostCustomRepository {

    Slice<PostDto> getPostsByOrderByCreatedDateDesc(Pageable pageable);
}
