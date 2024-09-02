package pro.sky.telegrambot.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface PictureService {

    Path uploadPicture(Long id, MultipartFile file) throws IOException;
}
