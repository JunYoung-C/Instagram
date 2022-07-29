package toyproject.instragram.common.file;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import toyproject.instragram.common.exception.form.addpost.EmptyFileException;
import toyproject.instragram.common.exception.form.addpost.InvalidImageExtensionException;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FileManagerTest {
    final String FILE_DIR = "C:/Users/chlwn/Desktop/프로젝트/개인 프로젝트/myproject1/instragram/src/test/resources/files/";
    FileManager fileManager = new FileManager(FILE_DIR);
    List<MultipartFile> multipartFiles;
    List<FileDto> fileDtos = new ArrayList<>();

    //    @BeforeAll
    void init() throws IOException {
        multipartFiles = List.of(
                createMockMultipartFile("files", "test-image1.pNg", MediaType.IMAGE_PNG_VALUE),
                createMockMultipartFile("files", "test-image2.JpEg", MediaType.IMAGE_JPEG_VALUE));
        fileDtos = fileManager.storeImageFiles(multipartFiles);
    }

    @DisplayName("전체 경로 조회")
    @Test
    void getFullPath() {
        //given
        //when
        String originalFileName = "originalFileName";
        String fullPath = fileManager.getFullPath(originalFileName);

        //then
        assertThat(fullPath).isEqualTo(FILE_DIR + originalFileName);
    }

    @Nested
    @DisplayName("여러 이미지 파일 저장")
    @Transactional
    class storeImageFilesTest {

        @DisplayName("성공")
        @Test
        void success() throws IOException {
            //given
            multipartFiles = List.of(
                    createMockMultipartFile("files", "test-image1.pNg", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile("files", "test-image2.JpEg", MediaType.IMAGE_JPEG_VALUE));

            //when
            //then
            assertThatNoException().isThrownBy(() -> fileDtos = fileManager.storeImageFiles(multipartFiles));
        }

        @DisplayName("실패 - 파일 없음")
        @Test
        void failByEmptyFile() throws IOException {
            //given
            multipartFiles = List.of(
                    createMockMultipartFile(
                            "files", "test-image1.pNg", MediaType.IMAGE_PNG_VALUE),
                    createEmptyMockMultipartFile(
                            "files", "test-image1.pNg", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile(
                            "files", "test-image2.JpEg", MediaType.IMAGE_JPEG_VALUE));

            //when
            //then
            assertThatThrownBy(() -> fileDtos = fileManager.storeImageFiles(multipartFiles))
                    .isExactlyInstanceOf(EmptyFileException.class);
        }

        @DisplayName("실패 - 유효하지 않은 확장자")
        @Test
        void failByExtension() throws IOException {
            //given
            multipartFiles = List.of(
                    createMockMultipartFile("files", "fail-test.avi", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile("files", "fail-test.docx", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile("files", "fail-test.mp4", MediaType.IMAGE_PNG_VALUE),
                    createMockMultipartFile("files", "fail-test.pdf", MediaType.IMAGE_PNG_VALUE));

            //when
            //then
            assertThatThrownBy(() -> {
                fileDtos = fileManager.storeImageFiles(multipartFiles);
            }).isExactlyInstanceOf(InvalidImageExtensionException.class);
        }
    }


    @Nested
    @DisplayName("이미지 파일 저장")
    @Transactional
    class storeImageFileTest {

        @DisplayName("성공")
        @Test
        void success() throws IOException {
            //given
            MockMultipartFile multipartFile =
                    createMockMultipartFile("files", "test-image1.pNg", MediaType.IMAGE_PNG_VALUE);

            //when
            //then
            assertThatNoException().isThrownBy(() -> fileDtos.add(fileManager.storeImageFile(multipartFile)));
        }

        @DisplayName("실패 - 파일 없음")
        @Test
        void failByEmptyFile() throws IOException {
            //given
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "files", "not-exist.png", MediaType.IMAGE_PNG_VALUE, InputStream.nullInputStream());

            //when
            //then
            assertThatThrownBy(() -> fileDtos.add(fileManager.storeImageFile(multipartFile)))
                    .isExactlyInstanceOf(EmptyFileException.class);
        }

        @DisplayName("실패 - 유효하지 않은 확장자")
        @ParameterizedTest
        @ValueSource(strings = {"avi", "docx", "mp4", "pdf"})
        void failByExtension(String extension) throws IOException {
            //given
            MockMultipartFile multipartFile = createMockMultipartFile(
                    "files", "fail-test." + extension, MediaType.IMAGE_PNG_VALUE);
            //when
            //then
            assertThatThrownBy(() -> fileDtos.add(fileManager.storeImageFile(multipartFile)))
                    .isExactlyInstanceOf(InvalidImageExtensionException.class);
        }
    }

    private MockMultipartFile createMockMultipartFile(
            String name, String originalFilename, String contentType) throws IOException {

        return new MockMultipartFile(
                name,
                originalFilename,
                contentType,
                new FileInputStream(new File(FILE_DIR + originalFilename)));
    }

    private MockMultipartFile createEmptyMockMultipartFile(
            String name, String originalFilename, String contentType) throws IOException {

        return new MockMultipartFile(name, originalFilename, contentType, InputStream.nullInputStream());
    }

    @AfterEach
    void destruct() {
        fileDtos.forEach(fileDto -> fileManager.deleteFile(fileDto.getStoreFileName(), fileDto.getExtension()));
    }
}