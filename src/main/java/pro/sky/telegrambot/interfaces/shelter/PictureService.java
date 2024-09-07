package pro.sky.telegrambot.interfaces.shelter;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * <p>интерфейс "обслуживающий картинки"</p>
 *
 * @Author manxix69
 */
public interface PictureService {
    Path uploadPicture(Long id, MultipartFile file) throws IOException;
}
