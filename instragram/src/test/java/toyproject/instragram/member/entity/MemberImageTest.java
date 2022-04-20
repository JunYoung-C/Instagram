package toyproject.instragram.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberImageTest {

    @DisplayName("저장된 파일 이름 조회")
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