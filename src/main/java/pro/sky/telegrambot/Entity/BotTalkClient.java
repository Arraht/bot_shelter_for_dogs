package pro.sky.telegrambot.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private String answerMessageId;
    private Boolean successOfSending;

    public BotTalkClient(Long id, String name, Long chatId, LocalDateTime timeSendMessage,
                         String initialSend, String answer, Boolean successOfSending) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
        this.timeSendMessage = timeSendMessage;
        this.initialSend = initialSend;
        this.answerMessageId = answer;
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
        if (this.successOfSending) {
            return "id клиена: " + this.id + "; имя клиента: "
                    + this.name + "; Кто отправил: " + this.initialSend +
                    "; время отправки: " + this.timeSendMessage + "; последний ответ: "
                    + this.answerMessageId + "; сообщение доставлено";
        } else {
            return "id клиена: " + this.id + "; имя клиента: "
                    + this.name + "; Кто отправил: " + this.initialSend +
                    "; время отправки: " + this.timeSendMessage + "; последний ответ: "
                    + this.answerMessageId + "; сообщение не доставлено";
        }
    }
}