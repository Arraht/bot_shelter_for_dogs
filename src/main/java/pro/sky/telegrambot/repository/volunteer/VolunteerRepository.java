package pro.sky.telegrambot.repository.volunteer;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Volunteers;

public interface VolunteerRepository extends JpaRepository<Volunteers, Long> {
    Volunteers findVolunteersByNickName(String nickName);
}
