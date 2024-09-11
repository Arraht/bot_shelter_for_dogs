package pro.sky.telegrambot.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;
import pro.sky.telegrambot.entity.PetPhoto;

/**
 * <p>интерфейс "Картинка домашнего животного" для взаимодействия с БД</p>
 *
 * @Author manxix69
 */
public interface PetPhotoRepository extends JpaRepository<PetPhoto, Long> {

    @Query(value = "select * from pet_photo where pet_id = :petId",nativeQuery = true)
    PetPhoto findByPetId(Long petId);
}
