package toyproject.instragram.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toyproject.instragram.common.exception.form.FormExceptionType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<FileDto> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileDto> fileDtos = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            fileDtos.add(storeFile(multipartFile));
        }
        return fileDtos;
    }

    public FileDto storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw EMPTY_FILE.getException();
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFileName);

        multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));

        return new FileDto(extractFileName(originalFileName), storeFileName, extension);
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
