package pro.sky.telegrambot.service.shelter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundPictureByShelterIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.DirectionToShelterPictureService;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;
import pro.sky.telegrambot.repository.shelter.DirectionToShelterPictureRepository;

import java.io.*;
import java.nio.file.Path;


/**
 * <p>класс DirectionToShelterPictureServiceImpl служит
 * для обработки картинок показывающих направление к приюту</p>
 *
 * @Author manxix69
 */
@Service
public class DirectionToShelterPictureServiceImpl implements DirectionToShelterPictureService {

    private final DirectionToShelterPictureRepository directionToShelterPictureRepository;
    private final ShelterService shelterService;
    private final PictureService pictureService;

    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(DirectionToShelterPictureServiceImpl.class);

    public DirectionToShelterPictureServiceImpl(
            DirectionToShelterPictureRepository directionToShelterPictureRepository
            , ShelterService shelterService
            , PictureService pictureService) {
        this.directionToShelterPictureRepository = directionToShelterPictureRepository;
        this.shelterService = shelterService;
        this.pictureService = pictureService;
    }

    /**
     * Ищет по идентификатору приюта картину. если ее нет то создает новую
     *
     * @param shelterId идентификатор приюта
     * @return возвращает найденную или созданную картинку для направления к приюту
     */
    private DirectionToShelterPicture findOrCreatePicture(Long shelterId) {
        logger.info("Was invoked method findOrCreatePicture shelter : {}", shelterId);
        DirectionToShelterPicture directionToShelterPicture = directionToShelterPictureRepository.findByShelterId(shelterId);
        if (directionToShelterPicture == null) {
            logger.debug("Picture not exists. A new Picture will be returned.");
            directionToShelterPicture = new DirectionToShelterPicture();
        }
        logger.debug("Picture, with shelterId = {}, {}", shelterId, directionToShelterPicture);
        return directionToShelterPicture;
    }

    /**
     * Ищет по идентификатору приюта картину.
     *
     * @param shelterId идентификатор приюта
     * @exception NotFoundPictureByShelterIdException если картина не найдена
     * @return возвращает найденную картинку для направления к приюту
     */
    @Override
    public DirectionToShelterPicture find(Long shelterId) {
        logger.info("Was invoked method find shelter : {}", shelterId);
        DirectionToShelterPicture directionToShelterPicture = directionToShelterPictureRepository.findByShelterId(shelterId);
        if (directionToShelterPicture == null){
            logger.error("DirectionToShelterPicture not exists for shelter : {}", shelterId);
            throw new NotFoundPictureByShelterIdException("Картина по id приюта не найдена в БД!");
        }
        logger.debug("DirectionToShelterPicture, with studentId = {}, {}", shelterId, directionToShelterPicture);
        return directionToShelterPicture;
    }

    /**
     * загружает в БД картинку.
     *
     * @param shelterId идентификатор приюта
     * @param directionToShelterPictureFile файл картинки
     * @exception IOException если возникли при проблемы при сохранеии/добавлении файла
     */
    @Override
    public void upload(Long shelterId, MultipartFile directionToShelterPictureFile) throws IOException {
        logger.info("Was invoked method upload : shelterId={} directionToShelterPicture.getName()={}", shelterId, directionToShelterPictureFile.getName());

        Shelter shelter = shelterService.findById(shelterId);
        Path filePath = pictureService.uploadPicture(shelter.getId(), directionToShelterPictureFile);

        DirectionToShelterPicture picture = findOrCreatePicture(shelterId);

        picture.setShelter(shelter);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(directionToShelterPictureFile.getSize());
        picture.setMediaType(directionToShelterPictureFile.getContentType());
        picture.setData(directionToShelterPictureFile.getBytes());
        directionToShelterPictureRepository.save(picture);
        logger.info("Picture was saved.");
    }


}
