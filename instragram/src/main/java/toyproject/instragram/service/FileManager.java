package toyproject.instragram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toyproject.instragram.FileDto;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileManager {

//    @Value("${file.dir}")
    private String fileDir;

    public FileManager(@Value("${file.dir}") String fileDir){
        this.fileDir = fileDir;
    }

    public FileDto storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(getFullPath(originalFilename)));
        } catch (IOException e) {
            // TODO 예외 클래스 만들면 수정하기
        }

        return new FileDto(
                extractFileName(originalFilename),
                UUID.randomUUID().toString(),
                extractExtension(originalFilename));
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
