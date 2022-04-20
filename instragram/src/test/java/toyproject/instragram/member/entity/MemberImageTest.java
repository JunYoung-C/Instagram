package toyproject.instragram.member.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberImageTest {

    @Test
    void getOriginalStoreFileName() {
        //given
        MemberImage memberImage = new MemberImage("upload", "store", "png");

        //when
        String originalStoreFileName = memberImage.getOriginalStoreFileName();

        //then
        assertThat(originalStoreFileName).isEqualTo("store.png");
    }
}