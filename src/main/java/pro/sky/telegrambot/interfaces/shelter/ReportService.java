package pro.sky.telegrambot.interfaces.shelter;

import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.entity.Volunteers;

import java.util.Collection;

/**
 * <p>интерфейс обслуживающий "отчеты"</p>
 *
 * @Author manxix69
 */
public interface ReportService {
    Report add(Report report);
    Report find(Report report);
    Report edit(Report report);
    Report remove(Report report);
    Report findById(Long reportId);

    Report approved(Long reportId, Boolean isApproved, Volunteers whomApproved);
    Collection<Report> getReportsByPetId(Long petId);
}
