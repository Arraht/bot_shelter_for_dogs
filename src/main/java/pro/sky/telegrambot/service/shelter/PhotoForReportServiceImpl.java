package pro.sky.telegrambot.service.shelter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.PhotoForReport;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.exception.NotFoundPictureByReportIdException;
import pro.sky.telegrambot.interfaces.shelter.PhotoForReportService;
import pro.sky.telegrambot.interfaces.shelter.PictureService;
import pro.sky.telegrambot.interfaces.shelter.ReportService;
import pro.sky.telegrambot.repository.shelter.PhotoForReportRepository;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class PhotoForReportServiceImpl implements PhotoForReportService {
    
    private final PhotoForReportRepository photoForReportRepository;
    private final ReportService reportService;
    private final PictureService pictureService;


    /**
     * <p>логирование работы класса<p/>
     */

    private Logger logger = LoggerFactory.getLogger(PhotoForReportServiceImpl.class);

    public PhotoForReportServiceImpl(
            PhotoForReportRepository photoForReportRepository
            , ReportService reportService
            , PictureService pictureService) {
        this.photoForReportRepository = photoForReportRepository;
        this.reportService = reportService;
        this.pictureService = pictureService;
    }


    /**
     * Ищет по идентификатору отчета картину. если ее нет то создает новую
     *
     * @param reportId идентификатор отчета
     * @return возвращает найденную или созданную картинку для направления к отчету
     */

    private PhotoForReport findOrCreatePicture(Long reportId) {
        logger.info("Was invoked method findOrCreatePicture report : {}", reportId);
        PhotoForReport photoForReport = photoForReportRepository.findByReportId(reportId);
        if (photoForReport == null) {
            logger.debug("Picture not exists. A new Picture will be returned.");
            photoForReport = new PhotoForReport();
        }
        logger.debug("Picture, with reportId = {}, {}", reportId, photoForReport);
        return photoForReport;
    }


    /**
     * Ищет по идентификатору отчета картину.
     *
     * @param reportId идентификатор отчета
     * @exception NotFoundPictureByReportIdException если картина не найдена
     * @return возвращает найденную картинку для направления к отчету
     */
    @Override
    public PhotoForReport find(Long reportId) {
        logger.info("Was invoked method find report : {}", reportId);
        PhotoForReport photoForReport = photoForReportRepository.findByReportId(reportId);
        if (photoForReport == null){
            logger.error("PhotoForReport not exists for report : {}", reportId);
            throw new NotFoundPictureByReportIdException("Картина по id отчета не найдена в БД!");
        }
        logger.debug("PhotoForReport, with reportId = {}, {}", reportId, photoForReport);
        return photoForReport;
    }

    /**
     * загружает в БД картинку.
     *
     * @param reportId идентификатор отчета
     * @param photoForReportFile файл картинки
     * @exception IOException если возникли при проблемы при сохранеии/добавлении файла
     */
    @Override
    public void upload(Long reportId, MultipartFile photoForReportFile) throws IOException {
        logger.info("Was invoked method upload : reportId={} photoForReport.getName()={}", reportId, photoForReportFile.getName());

        Report report = reportService.findById(reportId);
        Path filePath = pictureService.uploadPicture(report.getId(), photoForReportFile);

        PhotoForReport picture = findOrCreatePicture(reportId);

        picture.setReport(report);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(photoForReportFile.getSize());
        picture.setMediaType(photoForReportFile.getContentType());
        picture.setData(photoForReportFile.getBytes());
        photoForReportRepository.save(picture);
        logger.info("Picture was saved.");
    }
}
