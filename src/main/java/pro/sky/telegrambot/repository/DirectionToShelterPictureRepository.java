package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.Entity.DirectionToShelterPicture;

public interface DirectionToShelterPictureRepository extends JpaRepository<DirectionToShelterPicture, Long> {

    @Query(value = "select * from direction_to_shelter_picture where shelter_id = :shelterId",nativeQuery = true)
    DirectionToShelterPicture findByShelterId(Long shelterId);

}
