package toyproject.instragram.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.instragram.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Slice<Post> getPostsByOrderByCreatedDateDesc(Pageable pageable);
}
