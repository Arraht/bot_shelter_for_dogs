package pro.sky.telegrambot.repository.shelter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.entity.DirectionToShelterPicture;
import pro.sky.telegrambot.entity.Report;

import java.util.Collection;

/**
 * <p>репозиторий "отчеты" для взаимодействия с БД</p>
 *
 * @Author manxix69
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "select * from report where pet_id = :petId",nativeQuery = true)
    Collection<Report> findAllByPetId(Long petId);

}
