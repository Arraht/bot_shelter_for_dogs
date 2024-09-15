package pro.sky.telegrambot.repository.bot;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.VolunteersChatId;

public interface VolunteersChatIdRepository extends JpaRepository<VolunteersChatId, Long> {
    VolunteersChatId findVolunteersChatIdByVolunteerId(Long VolunteersId);
}