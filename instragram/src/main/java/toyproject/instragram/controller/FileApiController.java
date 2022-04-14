package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import toyproject.instragram.FileManager;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class FileApiController {
    private final FileManager fileManager;

    //TODO 예외클래스 변경
    @GetMapping("/images/{originalFileName}")
    public Resource getImage(@PathVariable String originalFileName) throws MalformedURLException {
        return new UrlResource("file:" + fileManager.getFullPath(originalFileName));
    }
}