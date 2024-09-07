package pro.sky.telegrambot.service.shelter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundPictureByShelterIdException;
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;
import pro.sky.telegrambot.repository.shelter.DirectionToShelterPictureRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DirectionToShelterPictureServiceImplTests {

    @Autowired
    private DirectionToShelterPictureServiceImpl directionToShelterPictureService;
    @Autowired
    private PictureService pictureService;

    @MockBean
    private DirectionToShelterPictureRepository directionToShelterPictureRepository;
    @MockBean
    private ShelterService shelterService;


    private DirectionToShelterPicture TEST_PICTURE = new DirectionToShelterPicture();
    private DirectionToShelterPicture EXPECTED_PICTURE = new DirectionToShelterPicture();

    /**
     * Ищет по идентификатору приюта картину. и получает ошибку когда не находт
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeReturnNotFoundPictureByShelterIdException() {
        Mockito.when(directionToShelterPictureRepository.findByShelterId(-1l)).thenReturn(null);

        Assertions.assertThrows(NotFoundPictureByShelterIdException.class, ()->directionToShelterPictureService.find(-1l));
    }

    /**
     * Ищет по идентификатору приюта картину.
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFind() {
        Mockito.when(directionToShelterPictureRepository.findByShelterId(1l)).thenReturn(TEST_PICTURE);
        EXPECTED_PICTURE = directionToShelterPictureService.find(1l);
        Assertions.assertEquals(EXPECTED_PICTURE, TEST_PICTURE);
    }

    /**
     * Ищет по идентификатору приюта картину. и подгружает файл в БД
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeUploadAndFindPicture() {
        DirectionToShelterPicture picture = new DirectionToShelterPicture();
        Shelter shelter = new Shelter();
        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        shelter.setName("TEST_name");
        shelter.setAddress("TEST_Address");
        shelter.setId(1l);

        Mockito.when(shelterService.findById(shelter.getId())).thenReturn(shelter);
        Mockito.when(directionToShelterPictureRepository.findByShelterId(shelter.getId())).thenReturn(picture);

        try {
            directionToShelterPictureService.upload(shelter.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }

        picture = directionToShelterPictureService.find(shelter.getId());
        Assertions.assertEquals(Arrays.toString(picture.getData()), Arrays.toString(arr));
    }

    /**
     * Ищет по идентификатору приюта картину. и возвращает ошибку - т.к. она не найдена
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeNotFoundAvatarByStudentIdExceptionOrReturnAvatar() {
        DirectionToShelterPicture picture = new DirectionToShelterPicture();
        Shelter shelter = new Shelter();
        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        shelter.setId(1l);

        Mockito.when(shelterService.findById(shelter.getId())).thenReturn(shelter);
        Mockito.when(directionToShelterPictureRepository.findByShelterId(shelter.getId())).thenReturn(null);

        try {
            directionToShelterPictureService.upload(shelter.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }
        Assertions.assertThrows(NotFoundPictureByShelterIdException.class, () -> directionToShelterPictureService.find(shelter.getId()));
    }
}
