package toyproject.instragram.post.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostFileTest {

    @DisplayName("저장된 파일 이름 조회")
    @Test
    void getOriginalStoreFileName() {
        //given
        PostFile postFile = new PostFile("upload", "store", "png");

        //when
        String originalStoreFileName = postFile.getOriginalStoreFileName();

        //then
        assertThat(originalStoreFileName).isEqualTo("store.png");
    }
}