package pro.sky.telegrambot.service.shelter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.PetPhoto;
import pro.sky.telegrambot.exception.NotFoundPictureByPetIdException;
import pro.sky.telegrambot.interfaces.shelter.PetPhotoService;
import pro.sky.telegrambot.interfaces.shelter.PetService;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.repository.shelter.PetPhotoRepository;

import java.io.IOException;
import java.nio.file.Path;

/**
 * <p>класс PetPhotoServiceImpl служит
 * для обработки картинок показывающих фото домашнего животного</p>
 *
 * @Author manxix69
 */
@Service
public class PetPhotoServiceImpl  implements PetPhotoService {

    private final PetPhotoRepository petPhotoRepository;
    private final PetService petService;
    private final PictureService pictureService;


    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(PetPhotoServiceImpl.class);

    public PetPhotoServiceImpl(
            PetPhotoRepository petPhotoRepository
            , PetService petService
            , PictureService pictureService) {
        this.petPhotoRepository = petPhotoRepository;
        this.petService = petService;
        this.pictureService = pictureService;
    }


    /**
     * Ищет по идентификатору домашнего животного картину. если ее нет то создает новую
     *
     * @param petId идентификатор приюта
     * @return возвращает найденную или созданную картинку домашнего животного
     */
    private PetPhoto findOrCreatePicture(Long petId) {
        logger.info("Was invoked method findOrCreatePicture pet : {}", petId);
        PetPhoto petPhoto = petPhotoRepository.findByPetId(petId);
        if (petPhoto == null) {
            logger.debug("Picture not exists. A new Picture will be returned.");
            petPhoto = new PetPhoto();
        }
        logger.debug("Picture, with shelterId = {}, {}", petId, petPhoto);
        return petPhoto;
    }


    /**
     * Ищет по идентификатору домашнего животного картину.
     *
     * @param petId идентификатор домашнего животного
     * @exception NotFoundPictureByPetIdException если картина не найдена
     * @return возвращает найденную картинку для домашнего животного
     */
    @Override
    public PetPhoto find(Long petId) {
        logger.info("Was invoked method find shelter : {}", petId);
        PetPhoto petPhoto = petPhotoRepository.findByPetId(petId);
        if (petPhoto == null){
            logger.error("PetPhoto not exists for pet : {}", petId);
            throw new NotFoundPictureByPetIdException("Картина по id доамашнего животного не найдена в БД!");
        }
        logger.debug("PetPhoto, with petId = {}, {}", petId, petPhoto);
        return petPhoto;
    }

    /**
     * загружает в БД картинку.
     *
     * @param petId идентификатор домашнего животного
     * @param petPhotoFile файл картинки
     * @exception IOException если возникли при проблемы при сохранеии/добавлении файла
     */
    @Override
    public void upload(Long petId, MultipartFile petPhotoFile) throws IOException {
        logger.info("Was invoked method upload : shelterId={} petPhoto.getName()={}", petId, petPhotoFile.getName());

        Pet pet = petService.findById(petId);
        Path filePath = pictureService.uploadPicture(pet.getId(), petPhotoFile);

        PetPhoto picture = findOrCreatePicture(petId);

        picture.setPet(pet);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(petPhotoFile.getSize());
        picture.setMediaType(petPhotoFile.getContentType());
        picture.setData(petPhotoFile.getBytes());
        petPhotoRepository.save(picture);
        logger.info("Picture was saved.");
    }

}
