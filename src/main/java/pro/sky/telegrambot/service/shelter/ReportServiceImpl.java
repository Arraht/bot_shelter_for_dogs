package pro.sky.telegrambot.service.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.exception.NotFoundReportByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ReportService;
import pro.sky.telegrambot.repository.shelter.ReportRepository;

import java.util.Collection;
import java.util.List;

/**
 * <p>сервис ReportServiceImpl для обработки отчетов по домашним животным</p>
 *
 * @Author manxix69
 */
@Service
public class ReportServiceImpl implements ReportService {

    /**
     * <p>репозиторий<p/>
     */
    private final ReportRepository reportRepository;

    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    /**
     * <p>конструктор<p/>
     */
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Добавляет в базу данных переданный отчет
     *
     * @param report структура отчет
     * @exception NotNullIdException если в переданном отчете заполнен id
     * @return возвращает добавленный отчет
     */
    @Override
    public Report add(Report report) {
        logger.info("Was invoked method add : report={}", report);
        if (report.getId() != null) {
            logger.error("An error occurred because report have id={}", report.getId());
            throw new NotNullIdException("При создании нового отчета не должно быть указано id в переданном запросе на сервер!");
        }
        return reportRepository.save(report);
    }

    /**
     * Ищет в базе данных переданный отчет по id
     *
     * @param report структура отчет
     * @exception NotFoundReportByIdException если отчет не найден по переданному id
     * @return возвращает найденный отчет
     */
    @Override
    public Report find(Report report) {
        logger.info("Was invoked method find : id={}", report.getId());
        Report foundedReport = reportRepository.findById(report.getId()).orElse(null);
        if (foundedReport == null) {
            logger.error("An error occurred because report not found by id={}", report.getId());
            throw new NotFoundReportByIdException("отчет не найден по ID!");
        }
        logger.debug("report={}", report);
        return foundedReport;
    }

    /**
     * Редактирует переданный отчет по id (полносностью заменяет ранее записанные данные на переданные)
     *
     * @param report структура отчет
     * @see  #find(Report)
     * @return возвращает отредактированный отчет
     */
    @Override
    public Report edit(Report report) {
        logger.info("Was invoked method edit : report={}", report);
        find(report);
        return reportRepository.save(report);
    }

    /**
     * Ищет в базе данных переданный отчет по id и удалает объект из базы данных.
     *
     * @param report структура отчет
     * @see  #find(Report)
     * @return возвращает удаленный отчет
     */
    @Override
    public Report remove(Report report) {
        logger.info("Was invoked method remove : report={}", report);
        Report foundedReport = find(report);
        reportRepository.deleteById(foundedReport.getId());
        return foundedReport;
    }

    /**
     * Ищет в базе данных переданный отчет по id и удалает объект из базы данных.
     *
     * @param reportId структура отчет
     * @throws NotFoundReportByIdException возращают ошибку если не найдет
     * @return возвращает найденный по идентификатору отчет
     */
    @Override
    public Report findById(Long reportId) {
        logger.info("Was invoked method findById : reportId={}", reportId);
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NotFoundReportByIdException("отчет не найден по ID!"));
        logger.info("Was invoked method findById : report={}", report);
        return report;
    }

    /**
     * Ищет в базе данных переданный отчет по id и ставит признак проверки отчета
     *
     * @param reportId структура отчет
     * @see  #findById(Long)
     * @throws NotFoundReportByIdException возращают ошибку если не найдет отчет в БД
     * @return возвращает отредактированный отчет
     */
    @Override
    public Report approved(Long reportId, Boolean isApproved, Volunteer whomApproved) {
        logger.info("Was invoked method approved : reportId={}, isApproved={}, whomApproved={},", reportId, isApproved,whomApproved);
        Report report = findById(reportId);
        report.setAccepted(isApproved);
        report.setWhoAccepted(whomApproved);
        logger.info("Was invoked method approved : report={}", report);
        return reportRepository.save(report);
    }

    @Override
    public Collection<Report> getReportsByPetId(Long petId) {
        logger.info("Was invoked method getReportByPetId : petId={}", petId);
        Collection<Report> reports = reportRepository.findAllByPetId(petId);
        logger.info("Was invoked method getReportByPetId : petId={}", petId);
        return reports;
    }
}
