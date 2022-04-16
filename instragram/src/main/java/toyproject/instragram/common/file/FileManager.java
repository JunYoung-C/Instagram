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

    public String getFullPath(String originalFilename) {
        return fileDir + originalFilename;
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

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));

        return new FileDto(extractFileName(originalFilename), storeFileName, extension);
    }

    private String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    private String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

}
