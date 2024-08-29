package pro.sky.telegrambot.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Игорь:
 * Сущность для хранения информации об общении клиента с ботом
 */
@Data
@Entity
public class BotTalkClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long chatId;
    private LocalDateTime timeSendMessage;
    private String initialSend;
    private Long answerId;
    private String message;
    private Boolean successOfSending;

    public BotTalkClient(Long id, Long answerId, String name, Long chatId, LocalDateTime timeSendMessage,
                         String initialSend, String message, Boolean successOfSending) {
        this.id = id;
        this.answerId = answerId;
        this.name = name;
        this.chatId = chatId;
        this.timeSendMessage = timeSendMessage;
        this.initialSend = initialSend;
        this.message = message;
        this.successOfSending = successOfSending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotTalkClient that = (BotTalkClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id клиена: " + this.id + "; имя клиента: "
                + this.name + "; Кто отправил: " + this.initialSend +
                "; время отправки: " + this.timeSendMessage + "; последний ответ: "
                + "; Статус доставки: " + this.message;
    }
}