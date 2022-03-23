package toyproject.instragram.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import toyproject.instragram.repository.dto.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static toyproject.instragram.entity.QComment.*;
import static toyproject.instragram.entity.QMember.*;
import static toyproject.instragram.entity.QPost.*;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PostDto> getPostsByOrderByCreatedDateDesc(Pageable pageable) {
        List<PostDto> content = getPostsWithoutComments(pageable);

        content.stream().forEach(postDto -> postDto.setCommentDtoList(getCommentMap(content).get(postDto.getId())));

        if (hasNext(pageable, content)) {
            return new SliceImpl(content.subList(0, pageable.getPageSize()), pageable, true);
        } else {
            return new SliceImpl(content, pageable, false);
        }
    }

    private List<PostDto> getPostsWithoutComments(Pageable pageable) {
        return queryFactory
                .select(new QPostDto(
                        post.id,
                        new QPostMemberDto(member.id,
                                member.profile.nickname,
                                member.profile.photoPath),
                        post.photoPath,
                        post.createdDate
                ))
                .from(post)
                .orderBy(post.createdDate.desc())
                .join(post.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private Map<Long, List<PostCommentDto>> getCommentMap(List<PostDto> content) {
        Map<Long, List<PostCommentDto>> commentMap = getPostCommentDtos(getPostIds(content)).stream()
                .collect(Collectors.groupingBy(PostCommentDto::getCommentId));
        return commentMap;
    }

    private List<Long> getPostIds(List<PostDto> content) {
        List<Long> postIds = content.stream()
                .map(postDto -> postDto.getId())
                .collect(Collectors.toList());
        return postIds;
    }

    private List<PostCommentDto> getPostCommentDtos(List<Long> postIds) {
        List<PostCommentDto> comments = queryFactory
                .select(new QPostCommentDto(comment.post.id, comment.text))
                .from(comment)
                .where(comment.post.id.in(postIds))
                .fetch();
        return comments;
    }

    private boolean hasNext(Pageable pageable, List<PostDto> content) {
        return content.size() == pageable.getPageSize() + 1;
    }
}
