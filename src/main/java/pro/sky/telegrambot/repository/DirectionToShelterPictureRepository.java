package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.Entity.DirectionToShelterPicture;
import pro.sky.telegrambot.Entity.Shelter;

public interface DirectionToShelterPictureRepository extends JpaRepository<DirectionToShelterPicture, Long> {

    DirectionToShelterPicture findByShelterId(Long shelterId);
}
