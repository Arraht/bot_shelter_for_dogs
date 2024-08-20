package pro.sky.telegrambot.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String command;
    private String message;

    public Answer(Long id, String command, String message) {
        this.id = id;
        this.command = command;
        this.message = message;
    }

    public Answer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "Id запроса: " + this.id + "; Вопрос-команда: " + this.command + "; ответ: " + this.message;
    }
}