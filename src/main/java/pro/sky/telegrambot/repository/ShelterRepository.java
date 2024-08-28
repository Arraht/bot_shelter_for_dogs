package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.Entity.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}
