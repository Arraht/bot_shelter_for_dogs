package pro.sky.telegrambot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public class VolunteersTalkClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameVolunteers;
    private Long volunteersId;
    private String nameClient;
    private Long chatId;
    private LocalDateTime appointmentTime;

    public VolunteersTalkClient(Long id, String nameVolunteers,
                                String nameClient,Long chatId,
                                LocalDateTime appointmentTime) {
        this.id = id;
        this.nameVolunteers = nameVolunteers;
        this.nameClient = nameClient;
        this.chatId = chatId;
        this.appointmentTime = appointmentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteersTalkClient that = (VolunteersTalkClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id записи: " + this.id + "; Имя волонтёра: " + this.nameVolunteers + "; Имя клиента: "
                + this.nameClient + "; chatId: " + this.chatId + "; врмя записи на приём: " + this.appointmentTime;
    }
}