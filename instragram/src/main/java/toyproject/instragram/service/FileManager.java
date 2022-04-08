package toyproject.instragram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toyproject.instragram.FileDto;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FileManager {

    private String fileDir;

    public FileManager(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

    public List<FileDto> storeFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(multipartFile -> storeFile(multipartFile).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Optional<FileDto> storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return Optional.empty();
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFilename);

        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));
        } catch (IOException e) {
            // TODO 예외 클래스 만들면 수정하기
        }

        return Optional.of(new FileDto(
                extractFileName(originalFilename),
                storeFileName,
                extension));
    }

    private String getFullPath(String originalFilename) {
        return fileDir + originalFilename;
    }

    private String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    private String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

}
