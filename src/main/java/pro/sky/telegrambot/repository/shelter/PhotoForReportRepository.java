package pro.sky.telegrambot.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.entity.PhotoForReport;

/**
 * <p>интерфейс "Картинка от клиента для отчета" для взаимодействия с БД</p>
 *
 * @Author manxix69
 */
public interface PhotoForReportRepository extends JpaRepository<PhotoForReport, Long> {
    @Query(value = "select * from photo_for_report where report_id = :reportId",nativeQuery = true)
    PhotoForReport findByReportId(Long reportId);
}
