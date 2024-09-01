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
    private Long chatId;
    private String name;
    private String command;
    private LocalDateTime timeSendMessage;
    private Boolean successOfSending;

    public BotTalkClient(Long id, Long chatId, String name, String command,
                         LocalDateTime timeSendMessage, Boolean successOfSending) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.command = command;
        this.timeSendMessage = timeSendMessage;
        this.successOfSending = successOfSending;
    }

    public BotTalkClient() {
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
                + this.name + "; время отправки: " + this.timeSendMessage + "; последняя команда:  "
                + "; Статус доставки: " + this.successOfSending;
    }
}