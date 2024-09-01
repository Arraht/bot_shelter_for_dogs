package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.Entity.DirectionToShelterPicture;
import pro.sky.telegrambot.Entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundPictureByShelterIdException;
import pro.sky.telegrambot.interfaces.DirectionToShelterPictureService;
import pro.sky.telegrambot.interfaces.ShelterService;
import pro.sky.telegrambot.repository.DirectionToShelterPictureRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class DirectionToShelterPictureServiceImpl implements DirectionToShelterPictureService {

    private final DirectionToShelterPictureRepository directionToShelterPictureRepository;
    private final ShelterService shelterService;

    private Logger logger = LoggerFactory.getLogger(DirectionToShelterPictureServiceImpl.class);

    public DirectionToShelterPictureServiceImpl(DirectionToShelterPictureRepository directionToShelterPictureRepository, ShelterService shelterService) {
        this.directionToShelterPictureRepository = directionToShelterPictureRepository;
        this.shelterService = shelterService;
    }

    @Value("${path.to.pictures.folder}")
    private String picturesDir;

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

    @Override
    public void upload(Long shelterId, MultipartFile directionToShelterPictureFile) throws IOException {
        logger.info("Was invoked method upload : shelterId={} directionToShelterPicture.getName()={}", shelterId, directionToShelterPictureFile.getName());

        Shelter shelter = shelterService.findById(shelterId);
        String filName = shelter + "." + getExtensions(directionToShelterPictureFile.getOriginalFilename());
        Path filePath = Path.of(picturesDir, filName);
        logger.debug("filePath={}",filePath);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = directionToShelterPictureFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            logger.error("An error occurred while transferring the file. {}{} , message", picturesDir,filName,e.getMessage());
            throw e;
        }

        DirectionToShelterPicture picture = find(shelterId);

        picture.setShelter(shelter);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(directionToShelterPictureFile.getSize());
        picture.setMediaType(directionToShelterPictureFile.getContentType());
        picture.setData(directionToShelterPictureFile.getBytes());
        directionToShelterPictureRepository.save(picture);
        logger.info("Picture was saved.");
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
