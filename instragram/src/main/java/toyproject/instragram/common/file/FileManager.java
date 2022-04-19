package toyproject.instragram.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static toyproject.instragram.common.exception.form.FormExceptionType.*;

@Component
public class FileManager {

    private String fileDir;

    public FileManager(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFullPath(String originalFileName) {
        return fileDir + originalFileName;
    }

    public List<FileDto> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileDto> fileDtos = new ArrayList<>();
        validateImageFiles(multipartFiles);
        for (MultipartFile multipartFile : multipartFiles) {
            fileDtos.add(storeImageFile(multipartFile));
        }
        return fileDtos;
    }

    private void validateImageFiles(List<MultipartFile> multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            validateEmptyFile(multipartFile);
            validateImageExtension(extractExtension(multipartFile.getOriginalFilename()));
        }
    }

    public FileDto storeImageFile(MultipartFile multipartFile) throws IOException {
        validateEmptyFile(multipartFile);

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFileName);

        validateImageExtension(extension);

        multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));

        return new FileDto(extractFileName(originalFileName), storeFileName, extension);
    }

    private void validateEmptyFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw EMPTY_FILE.getException();
        }
    }

    private void validateImageExtension(String extension) {
        if (!extension.matches("^(?i)(jpg|png|jpeg|)$")) {
            throw INVALID_IMAGE_EXTENSION.getException();
        }
    }

    private String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    private String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    public void deleteFile(String storeFileName, String extension) {
        File storedFile = new File(getFullPath(storeFileName + "." + extension));

        if (storedFile.exists()) {
            storedFile.delete();
        }
    }
}
