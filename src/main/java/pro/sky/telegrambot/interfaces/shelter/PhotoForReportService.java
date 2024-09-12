package pro.sky.telegrambot.interfaces.shelter;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;
import pro.sky.telegrambot.entity.PhotoForReport;

import java.io.IOException;

/**
 * <p>интерфейс обслуживающий "Картинку для отчета"</p>
 *
 * @Author manxix69
 */
public interface PhotoForReportService {
    PhotoForReport find(Long reportId);
    void upload(Long reportId, MultipartFile multipartFile) throws IOException;
}
