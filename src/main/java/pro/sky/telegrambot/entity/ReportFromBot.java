package pro.sky.telegrambot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность отчета из бота
 */
@Data
@Entity
public class ReportFromBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fileSize;
    private String fileId;
    private Long chatId;
    private String userName;
    private byte[] dataReportBot;
    private LocalDateTime timeSendReport;
    private String messageReportFromBot;
    private LocalDateTime timeComplete;

    public ReportFromBot(Long id, Long fileSize, String fileId, Long chatId, String userName, byte[] dataReportBot,
                         String messageReportFromBot) {
        this.id = id;
        this.fileSize = fileSize;
        this.fileId = fileId;
        this.chatId = chatId;
        this.userName = userName;
        this.dataReportBot = dataReportBot;
        this.messageReportFromBot = messageReportFromBot;
    }

    public ReportFromBot(Long id, Long fileSize, String fileId, Long chatId, String userName,
                         byte[] dataReportBot, LocalDateTime timeSendReport) {
        this.id = id;
        this.fileSize = fileSize;
        this.fileId = fileId;
        this.chatId = chatId;
        this.userName = userName;
        this.dataReportBot = dataReportBot;
        this.timeSendReport = timeSendReport;
    }
    public ReportFromBot(Long id, Long fileSize, String fileId, Long chatId, String userName,
                         byte[] dataReportBot, LocalDateTime timeSendReport, LocalDateTime timeComplete) {
        this.id = id;
        this.fileSize = fileSize;
        this.fileId = fileId;
        this.chatId = chatId;
        this.userName = userName;
        this.dataReportBot = dataReportBot;
        this.timeSendReport = timeSendReport;
        this.timeComplete = timeComplete;
    }

    public ReportFromBot() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportFromBot that = (ReportFromBot) o;
        return Objects.equals(userName, that.userName) && Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, chatId);
    }

    public String toString() {
        return "id отчёта: " + this.id + "; chatID: " + this.chatId + "; Username: " + this.userName + "; " +
                "время отчёта: " + this.timeSendReport;
    }
}