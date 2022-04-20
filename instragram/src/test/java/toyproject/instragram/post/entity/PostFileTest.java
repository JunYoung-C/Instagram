package toyproject.instragram.post.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostFileTest {

    @Test
    void getOriginalStoreFileName() {
        //given
        PostFile postFile = new PostFile(null, "upload", "store", "png");

        //when
        String originalStoreFileName = postFile.getOriginalStoreFileName();

        //then
        assertThat(originalStoreFileName).isEqualTo("store.png");
    }
}