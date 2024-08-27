package pro.sky.telegrambot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Игорь:
 * Сущность для хранения информации о волонтёрах в БД
 */
@Data
@Entity
public class Volunteers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickName;
    private LocalDateTime workingFirstTime;
    private LocalDateTime workingLastTime;

    public Volunteers(Long id, String name, String nickName,
                      LocalDateTime workingFirstTime,
                      LocalDateTime workingLastTime) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.workingFirstTime = workingFirstTime;
        this.workingLastTime = workingLastTime;
    }

    public Volunteers() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteers volunteer = (Volunteers) o;
        return Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id волонтёра: " + this.id + "; Имя волонёра: " + this.name + "; Никнейм волонтёра в телеграме: "
                + this.nickName + "; Время работы волонтёра: С "
                + this.workingFirstTime + " до " + this.workingLastTime;
    }
}