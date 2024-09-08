package pro.sky.telegrambot.repository.volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

}
