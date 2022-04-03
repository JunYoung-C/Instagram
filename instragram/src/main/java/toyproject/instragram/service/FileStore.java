package toyproject.instragram.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName, String extension) {
        return fileDir + fileName + "." + extension;
    }

    public String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    public String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public String createStoreFileName() {
        return UUID.randomUUID().toString();
    }

    public void storeFile(MultipartFile multipartFile, String filePath) {
        if (multipartFile.isEmpty()) {
            return;
        }

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            // TODO 예외 클래스 만들면 수정하기
            e.printStackTrace();
        }
    }
}
