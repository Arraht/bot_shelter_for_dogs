package pro.sky.telegrambot.repository.bot;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.ReportFromBot;

public interface ReportFromBotRepository extends JpaRepository<ReportFromBot, Long> {
    ReportFromBot findReportFromBotByUserName(String userName);
}