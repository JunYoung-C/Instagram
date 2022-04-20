package toyproject.instragram.post.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    Post post;

    @BeforeEach
    void init() {
        post = new Post(null, "게시물");
    }

    @DisplayName("게시물 파일 추가")
    @Test
    void addPostFile() {
        //given
        PostFile postFile = new PostFile("upload", "store", "png");

        //when
        post.addPostFile(postFile);

        //then
        assertThat(post.getPostFiles()).containsAnyOf(postFile);
        assertThat(postFile.getPost()).isEqualTo(post);
    }

    @DisplayName("게시물 글 수정")
    @Test
    void changeText() {
        //given
        String modifiedText = "수정";

        //when
        post.changeText(modifiedText);

        //then
        assertThat(post.getText()).isEqualTo(modifiedText);
    }
}