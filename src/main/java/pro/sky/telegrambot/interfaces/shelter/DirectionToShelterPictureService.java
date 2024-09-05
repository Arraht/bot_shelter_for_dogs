package pro.sky.telegrambot.interfaces.shelter;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;

import java.io.IOException;

public interface DirectionToShelterPictureService {
    DirectionToShelterPicture find(Long ShelterId);
    void upload(Long ShelterId, MultipartFile directionToShelterPicture) throws IOException;
}
