package pro.sky.telegrambot.service.shelter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.ShelterService;
import pro.sky.telegrambot.repository.shelter.ShelterRepository;

/**
 * <p>класс Приют для обработки данных с фронта по приютам</p>
 *
 * @Author manxix69
 */
@Service
public class ShelterServiceImpl implements ShelterService {
    /**
     * <p>репозиторий<p/>
     */
    private final ShelterRepository shelterRepository;

    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);
    /**
     * <p>конструктор<p/>
     */
    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }
    /**
     * Добавляет в базу данных переданный приют
     *
     * @param shelter структура приют
     * @exception NotNullIdException если в переданном приюте заполнен id
     */
    @Override
    public Shelter add(Shelter shelter) {
        logger.info("Was invoked method add : shelter={}", shelter);
        if (shelter.getId() != null) {
            logger.error("An error occurred because shelter have id={}", shelter.getId());
            throw new NotNullIdException("При создании нового приюта не должно быть указано id в переданном запросе на сервер!");
        }
        return shelterRepository.save(shelter);
    }

    /**
     * Ищет в базе данных переданный приют по id
     *
     * @param shelter структура приют
     * @exception NotFoundShelterByIdException если приют не найден по переданному id
     */
    @Override
    public Shelter find(Shelter shelter) {
        logger.info("Was invoked method find : id={}", shelter.getId());
        Shelter foundedShelter = shelterRepository.findById(shelter.getId()).orElse(null);
        if (foundedShelter == null) {
            logger.error("An error occurred because shelter not found by id={}", shelter.getId());
            throw new NotFoundShelterByIdException("Приют не найден по ID!");
        }
        logger.debug("faculty={}", shelter);
        return foundedShelter;
    }

    /**
     * Редактирует переданный приют по id (полносностью заменяет ранее записанные данные на переданные)
     *
     * @param shelter структура приют
     * @see  #find(Shelter)
     */
    @Override
    public Shelter edit(Shelter shelter) {
        logger.info("Was invoked method edit : shelter={}", shelter);
        find(shelter);
        return shelterRepository.save(shelter);
    }

    /**
     * Ищет в базе данных переданный приют по id и удалает объект из базы данных.
     *
     * @param shelter структура приют
     * @see  #find(Shelter)
     */
    @Override
    public Shelter remove(Shelter shelter) {
        logger.info("Was invoked method remove : shelter={}", shelter);
        Shelter foundedShelter = find(shelter);
        shelterRepository.deleteById(foundedShelter.getId());
        return foundedShelter;
    }

    /**
     * Ищет в базе данных переданный приют по id и удалает объект из базы данных.
     *
     * @param shelterId структура приют
     * @throws NotFoundShelterByIdException возращают ошибку если не найдет
     */
    @Override
    public Shelter findById(Long shelterId) {
        logger.info("Was invoked method findById : shelterId={}", shelterId);
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow(() -> new NotFoundShelterByIdException("Приют не найден по ID!"));
        logger.info("Was invoked method findById : shelter={}", shelter);
        return shelter;
    }
}
