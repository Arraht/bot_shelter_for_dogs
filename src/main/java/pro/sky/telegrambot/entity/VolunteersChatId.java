package pro.sky.telegrambot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class VolunteersChatId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long volunteerId;
    private Long chatId;

    public VolunteersChatId(Long id, Long volunteersId, Long chatId) {
        this.id = id;
        this.volunteerId = volunteersId;
        this.chatId = chatId;
    }

    public VolunteersChatId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteersChatId that = (VolunteersChatId) o;
        return Objects.equals(id, that.id) && Objects.equals(volunteerId, that.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volunteerId);
    }
}