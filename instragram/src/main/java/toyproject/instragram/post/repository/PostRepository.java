package toyproject.instragram.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.instragram.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    @EntityGraph(attributePaths = {"member", "member.memberImage"})
    Slice<Post> getPostsByOrderByIdDesc(Pageable pageable);
}
