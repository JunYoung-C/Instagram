package toyproject.instragram.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import toyproject.instragram.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class FileManagerTest {

    final String FILE_DIR = "C:/Users/chlwn/Desktop/myproject1/instragram/src/test/resources/files/";
    FileManager fileManager = new FileManager(FILE_DIR);

    @Test
    void storeFile() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "files",
                "test-image1.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(new File(FILE_DIR + "test-image1.png")));

        //when
//        FileDto fileDto = fileManager.storeFile(mockMultipartFile);

        //then
        // TODO 추후 테스트 코드 보완
//        assertThat(storeFileName1).isNotEqualTo(storeFileName2);
    }

}