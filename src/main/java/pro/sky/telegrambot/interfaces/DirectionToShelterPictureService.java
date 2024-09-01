package pro.sky.telegrambot.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.Entity.DirectionToShelterPicture;

import java.io.IOException;

public interface DirectionToShelterPictureService {
    DirectionToShelterPicture find(Long ShelterId);
    void upload(Long ShelterId, MultipartFile directionToShelterPicture) throws IOException;
}
