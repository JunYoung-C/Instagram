package toyproject.instragram.common.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDto {

    private String uploadFileName;
    private String storeFileName;
    private String extension;
}
