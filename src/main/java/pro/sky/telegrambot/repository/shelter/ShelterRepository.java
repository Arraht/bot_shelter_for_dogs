package pro.sky.telegrambot.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Shelter;

/**
 * <p>интерфейс "Приют" для взаимодействия с БД</p>
 *
 * @Author manxix69
 */
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}
