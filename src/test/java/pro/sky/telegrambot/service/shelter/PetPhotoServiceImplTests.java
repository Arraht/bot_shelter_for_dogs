package pro.sky.telegrambot.service.shelter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.*;
import pro.sky.telegrambot.exception.NotFoundPictureByPetIdException;
import pro.sky.telegrambot.interfaces.shelter.PetPhotoService;
import pro.sky.telegrambot.interfaces.shelter.PetService;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.interfaces.shelter.ReportService;
import pro.sky.telegrambot.repository.shelter.PetPhotoRepository;

import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PetPhotoServiceImplTests {
    @Autowired
    private PetPhotoService petPhotoService;
    @Autowired
    private PictureService pictureService;

    @MockBean
    private PetPhotoRepository petPhotoRepository;
    @MockBean
    private PetService petService;


    private PetPhoto TEST_PICTURE = new PetPhoto();
    private PetPhoto EXPECTED_PICTURE = new PetPhoto();

    @BeforeEach
    void init(){
        TEST_PICTURE.setId(1l);
        TEST_PICTURE.setPet(null);
        TEST_PICTURE.setData( new byte[]{} );
        TEST_PICTURE.setFilePath(null);
        TEST_PICTURE.setFileSize(0);
        TEST_PICTURE.setMediaType(null);
    }

    private void assertsObject(PetPhoto expected, PetPhoto actual) {

        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getPet(),actual.getPet());
        Assertions.assertEquals(expected.getData(),actual.getData());
        Assertions.assertEquals(expected.getFilePath(),actual.getFilePath());
        Assertions.assertEquals(expected.getFileSize(),actual.getFileSize());
        Assertions.assertEquals(expected.getMediaType(),actual.getMediaType());

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
        Assertions.assertTrue(expected.equals(actual));

    }

    /**
     * Ищет по идентификатору приюта картину. и получает ошибку когда не находит
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeReturnNotFoundPictureByPetPhotoIdException() {
        Mockito.when(petPhotoRepository.findByPetId(-1l)).thenReturn(null);

        Assertions.assertThrows(NotFoundPictureByPetIdException.class, ()->petPhotoService.find(-1l));
    }

    /**
     * Ищет по идентификатору приюта картину.
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFind() {
        Mockito.when(petPhotoRepository.findByPetId(1l)).thenReturn(TEST_PICTURE);
        EXPECTED_PICTURE = petPhotoService.find(1l);
        assertsObject(EXPECTED_PICTURE, TEST_PICTURE);
    }

    /**
     * Ищет по идентификатору приюта картину. и подгружает файл в БД
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeUploadAndFindPicture() {
        PetPhoto picture = new PetPhoto();
        PetPhoto petPhoto = new PetPhoto();

        Pet pet = new Dog();

        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        petPhoto.setPet(pet);
        petPhoto.setPet(pet);
        petPhoto.setId(1l);

        Mockito.when(petService.findById(pet.getId())).thenReturn(pet);
        Mockito.when(petPhotoRepository.findByPetId(petPhoto.getId())).thenReturn(picture);

        try {
            petPhotoService.upload(pet.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }

        picture = petPhotoService.find(petPhoto.getId());
        Assertions.assertEquals(Arrays.toString(picture.getData()), Arrays.toString(arr));
    }

    /**
     * Ищет по идентификатору приюта картину. и возвращает ошибку - т.к. она не найдена
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeNotFoundPictureByPetIdExceptionOrReturnAvatar() {
        PetPhoto picture = new PetPhoto();
        Pet pet = new Dog();
        pet.setId(1L);
        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        pet.setId(1l);

        Mockito.when(petService.findById(pet.getId())).thenReturn(pet);
        Mockito.when(petPhotoRepository.findByPetId(pet.getId())).thenReturn(null);

        try {
            petPhotoService.upload(pet.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }
        Assertions.assertThrows(NotFoundPictureByPetIdException.class, () -> petPhotoService.find(pet.getId()));
    }
}
