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
import pro.sky.telegrambot.entity.*;
import pro.sky.telegrambot.exception.NotFoundReportByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ReportService;
import pro.sky.telegrambot.repository.shelter.ReportRepository;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTests {
    @Autowired
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    
    private Report TEST_REPORT = new Report();
    private Report EXPECTED_REPORT = new Report();

    @BeforeEach
    void init(){
        TEST_REPORT.setId(1l);
        TEST_REPORT.setPet(null);
        TEST_REPORT.setClient(null);
        TEST_REPORT.setAccepted(false);
        TEST_REPORT.setWhoAccepted(null);
        TEST_REPORT.setTimeReceivedText(LocalDateTime.now());
        TEST_REPORT.setTimeReceivedPhoto(LocalDateTime.now());
        TEST_REPORT.setTimeCreated(LocalDateTime.now());
    }

    private void assertsObject(Report expected, Report actual) {
        Assertions.assertEquals(expected.getId(),actual.getId());
        Assertions.assertEquals(expected.getPet(),actual.getPet());
        Assertions.assertEquals(expected.getClient(),actual.getClient());
        Assertions.assertEquals(expected.getAccepted(),actual.getAccepted());
        Assertions.assertEquals(expected.getWhoAccepted(),actual.getWhoAccepted());
        Assertions.assertEquals(expected.getTimeReceivedText(),actual.getTimeReceivedText());
        Assertions.assertEquals(expected.getTimeReceivedPhoto(),actual.getTimeReceivedPhoto());
        Assertions.assertEquals(expected.getTimeCreated(),actual.getTimeCreated());

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
        Assertions.assertTrue(expected.equals(actual));
    }


    @Test
    public void shouldBeReturnNotFoundReportByIdException() {
        Mockito.when(reportRepository.findById(-1l)).thenReturn(null);

        Assertions.assertThrows(NotFoundReportByIdException.class, ()-> reportService.find(TEST_REPORT));
    }

    @Test
    public void shouldBeFind() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));
        EXPECTED_REPORT = reportService.find(TEST_REPORT);
        assertsObject(EXPECTED_REPORT, TEST_REPORT);
    }

    @Test
    public void shouldBeEdit() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));
        Mockito.when(reportRepository.save(TEST_REPORT)).thenReturn(TEST_REPORT);

        TEST_REPORT.setTimeReceivedText(LocalDateTime.now());
        TEST_REPORT.setClient(new Client());

        EXPECTED_REPORT = reportService.edit(TEST_REPORT);
        assertsObject(EXPECTED_REPORT, TEST_REPORT);
    }

    @Test
    public void shouldBeRemove() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));


        EXPECTED_REPORT = reportService.remove(TEST_REPORT);
        assertsObject(EXPECTED_REPORT, TEST_REPORT);
    }

    @Test
    public void shouldBeAdd() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));
        Mockito.when(reportRepository.save(TEST_REPORT)).thenReturn(TEST_REPORT);

        TEST_REPORT.setId(null);
        EXPECTED_REPORT.setId(null);

        EXPECTED_REPORT = reportService.add(TEST_REPORT);
        assertsObject(EXPECTED_REPORT, TEST_REPORT);
    }

    @Test
    public void shouldBeThrownExceptionAdd() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));

        Assertions.assertThrows(NotNullIdException.class, () -> reportService.add(TEST_REPORT));
    }

    @Test
    public void shouldBeFindById() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));

        EXPECTED_REPORT = reportService.findById(TEST_REPORT.getId());
        assertsObject(EXPECTED_REPORT, TEST_REPORT);

    }

    @Test
    public void shouldBThrown() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundReportByIdException.class, () -> reportService.findById(TEST_REPORT.getId()));
    }


    @Test
    public void shouldBeSetApproveReportById() {
        Mockito.when(reportRepository.findById(TEST_REPORT.getId())).thenReturn(Optional.of(TEST_REPORT));
        Mockito.when(reportRepository.save(TEST_REPORT)).thenReturn(TEST_REPORT);

        EXPECTED_REPORT = reportService.approved(TEST_REPORT.getId(), false, new Volunteers());

        assertsObject(EXPECTED_REPORT, TEST_REPORT);

    }

    @Test
    public void shouldBeFindReportsByPetId() {
        Pet pet = new Dog();
        pet.setId(1l);
        TEST_REPORT.setPet(pet);
        Collection<Report> reportsExcepted = new ArrayList<>();
        reportsExcepted.add(TEST_REPORT);

        Collection<Report> reportsActual;

        Mockito.when(reportRepository.findAllByPetId(TEST_REPORT.getPet().getId())).thenReturn(reportsExcepted);
        Mockito.when(reportRepository.save(TEST_REPORT)).thenReturn(TEST_REPORT);

        reportsActual = reportService.getReportsByPetId(TEST_REPORT.getPet().getId());

        Assertions.assertEquals(reportsActual.size(), reportsExcepted.size());

    }

}
