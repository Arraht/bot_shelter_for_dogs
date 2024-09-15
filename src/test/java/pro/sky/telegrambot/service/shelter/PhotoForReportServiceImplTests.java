package pro.sky.telegrambot.service.report;

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
import pro.sky.telegrambot.exception.NotFoundPictureByReportIdException;
import pro.sky.telegrambot.interfaces.shelter.PhotoForReportService;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.interfaces.shelter.ReportService;
import pro.sky.telegrambot.repository.shelter.PhotoForReportRepository;

import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PhotoForReportServiceImplTests {
    @Autowired
    private PhotoForReportService photoForReportService;
    @Autowired
    private PictureService pictureService;

    @MockBean
    private PhotoForReportRepository photoForReportRepository;
    @MockBean
    private ReportService reportService;


    private PhotoForReport TEST_PICTURE = new PhotoForReport();
    private PhotoForReport EXPECTED_PICTURE = new PhotoForReport();

    @BeforeEach
    void init(){
        TEST_PICTURE.setId(1l);
        TEST_PICTURE.setReport(null);
        TEST_PICTURE.setData( new byte[]{} );
        TEST_PICTURE.setFilePath(null);
        TEST_PICTURE.setFileSize(0);
        TEST_PICTURE.setMediaType(null);
    }

    private void assertsObject(PhotoForReport expected, PhotoForReport actual) {

        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getReport(),actual.getReport());
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
    public void shouldBeReturnNotFoundPictureByReportIdException() {
        Mockito.when(photoForReportRepository.findByReportId(-1l)).thenReturn(null);

        Assertions.assertThrows(NotFoundPictureByReportIdException.class, ()->photoForReportService.find(-1l));
    }

    /**
     * Ищет по идентификатору приюта картину.
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeFind() {
        Mockito.when(photoForReportRepository.findByReportId(1l)).thenReturn(TEST_PICTURE);
        EXPECTED_PICTURE = photoForReportService.find(1l);
        assertsObject(EXPECTED_PICTURE, TEST_PICTURE);
    }

    /**
     * Ищет по идентификатору приюта картину. и подгружает файл в БД
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeUploadAndFindPicture() {
        PhotoForReport picture = new PhotoForReport();
        Report report = new Report();
        Client client = new Client();
        Pet pet = new Dog();

        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        report.setClient(client);
        report.setPet(pet);
        report.setId(1l);

        Mockito.when(reportService.findById(report.getId())).thenReturn(report);
        Mockito.when(photoForReportRepository.findByReportId(report.getId())).thenReturn(picture);

        try {
            photoForReportService.upload(report.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }

        picture = photoForReportService.find(report.getId());
        Assertions.assertEquals(Arrays.toString(picture.getData()), Arrays.toString(arr));
    }

    /**
     * Ищет по идентификатору приюта картину. и возвращает ошибку - т.к. она не найдена
     * @Note для тестирования используется Mockito
     */
    @Test
    public void shouldBeNotFoundAvatarByStudentIdExceptionOrReturnAvatar() {
        PhotoForReport picture = new PhotoForReport();
        Report report = new Report();
        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        picture.setData(arr);
        report.setId(1l);

        Mockito.when(reportService.findById(report.getId())).thenReturn(report);
        Mockito.when(photoForReportRepository.findByReportId(report.getId())).thenReturn(null);

        try {
            photoForReportService.upload(report.getId(), multipartFile);
        } catch (IOException e) {
            Assertions.assertNull(e);
        }
        Assertions.assertThrows(NotFoundPictureByReportIdException.class, () -> photoForReportService.find(report.getId()));
    }
}
