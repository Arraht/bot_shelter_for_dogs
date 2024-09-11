package pro.sky.telegrambot.repository.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.entity.Pet;

import java.util.Optional;

/**
 * <p>интерфейс "Домашнее животное" для взаимодействия с БД</p>
 *
 * @Author manxix69
 */
public interface PetRepository extends JpaRepository<Pet,Long> {
    @Query(nativeQuery = true, value = "select * from pet where id = :id and class_pet = :classPet")
    Optional<Pet> findById(@Param("id") Long id, @Param("classPet") String classPet);
}
