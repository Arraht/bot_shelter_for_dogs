package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Игорь:
 * Сущность для хранения информации о волонтёрах в БД
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDateTime getWorkingFirstTime() {
        return workingFirstTime;
    }

    public void setWorkingFirstTime(LocalDateTime workingFirstTime) {
        this.workingFirstTime = workingFirstTime;
    }

    public LocalDateTime getWorkingLastTime() {
        return workingLastTime;
    }

    public void setWorkingLastTime(LocalDateTime workingLastTime) {
        this.workingLastTime = workingLastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteers that = (Volunteers) o;
        return Objects.equals(id, that.id) && Objects.equals(nickName, that.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName);
    }

    public String toString() {
        return "id волонтёра: " + this.id + "; Имя волонёра: " + this.name + "; Никнейм волонтёра в телеграме: "
                + this.nickName + "; Время работы волонтёра: С "
                + this.workingFirstTime + " до " + this.workingLastTime;
    }
}