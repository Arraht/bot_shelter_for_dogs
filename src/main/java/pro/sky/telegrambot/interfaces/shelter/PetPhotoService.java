package pro.sky.telegrambot.interfaces.shelter;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.PetPhoto;

import java.io.IOException;

/**
 * <p>интерфейс обслуживающий "Фото Домашнее животное" /p>
 *
 * @Author manxix69
 */
public interface PetPhotoService {
    PetPhoto find(Long petId);
    void upload(Long petId, MultipartFile file) throws IOException;
}
