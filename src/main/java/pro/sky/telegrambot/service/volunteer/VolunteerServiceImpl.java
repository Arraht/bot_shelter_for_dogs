package pro.sky.telegrambot.service.volunteer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Volunteers;
import pro.sky.telegrambot.exception.NotFoundVolunteerByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.volunteer.VolunteerRepository;
import pro.sky.telegrambot.service.shelter.ShelterServiceImpl;

import java.util.List;

/**
 * <p>класс Волонтер для обработки данных с фронта по волонтерам</p>
 *
 * @Author manxix69
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {

    /**
     * <p>репозиторий<p/>
     */
    private final VolunteerRepository volunteerRepository;

    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(ShelterServiceImpl.class);

    /**
     * <p>конструктор<p/>
     */
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Добавляет в базу данных переданного волонтера
     *
     * @param volunteers структура волонтер
     * @return возвращает добавленного волонтера
     * @throws NotNullIdException если в переданном объекте заполнен id
     */
    @Override
    public Volunteers add(Volunteers volunteers) {
        logger.info("Was invoked method add : volunteer={}", volunteers);
        if (volunteers.getId() != null) {
            logger.error("An error occurred because object " + volunteers.getClass() + " have id={}", volunteers.getId());
            throw new NotNullIdException("При создании нового объекта " + volunteers + " не должно быть указано id в переданном запросе на сервер!");
        }
        return volunteerRepository.save(volunteers);
    }

    /**
     * Ищет в базе данных переданного волотера по id
     *
     * @param volunteers структура волонтер
     * @return возвращает найденного волонтера
     * @throws NotFoundVolunteerByIdException если волонтер не найден по переданному id
     */
    @Override
    public Volunteers find(Volunteers volunteers) {
        logger.info("Was invoked method find : id={}", volunteers.getId());
        Volunteers foundedVolunteers = volunteerRepository.findById(volunteers.getId()).orElse(null);
        if (foundedVolunteers == null) {
            logger.error("An error occurred because object " + volunteers.getClass() + " not found by id={}", volunteers.getId());
            throw new NotFoundVolunteerByIdException("Волонтер не найден по ID!");
        }
        logger.debug("volunteer={}", volunteers);
        return foundedVolunteers;
    }

    /**
     * Редактирует переданного волонтера по id (полносностью заменяет ранее записанные данные на переданные)
     *
     * @param volunteers структура волонтер
     * @return возвращает отредактированного волонтера
     * @see #find(Volunteers)
     */
    @Override
    public Volunteers edit(Volunteers volunteers) {
        logger.info("Was invoked method edit : volunteer={}", volunteers);
        find(volunteers);
        return volunteerRepository.save(volunteers);
    }

    /**
     * Ищет в базе данных переданного волонтера по id и удалает объект из базы данных.
     *
     * @param volunteers структура волонтер
     * @return возвращает удаленного волонтера
     * @see #find(Volunteers)
     */
    @Override
    public Volunteers remove(Volunteers volunteers) {
        logger.info("Was invoked method remove : volunteer={}", volunteers);
        Volunteers foundedVolunteers = find(volunteers);
        volunteerRepository.deleteById(foundedVolunteers.getId());
        return foundedVolunteers;
    }

    /**
     * Ищет в базе данных переданного волонтера по id и удалает объект из базы данных.
     *
     * @param volunteerId структура волонтер
     * @return возвращает найденный по идентификатору волонтера
     * @throws NotFoundVolunteerByIdException возращают ошибку если не найдет
     */
    @Override
    public Volunteers findById(Long volunteerId) {
        logger.info("Was invoked method findById : volunteerId={}", volunteerId);
        Volunteers volunteers = volunteerRepository.findById(volunteerId).orElseThrow(() -> new NotFoundVolunteerByIdException("Волонтер не найден по ID!"));
        logger.info("Was invoked method findById : volunteer={}", volunteers);
        return volunteers;
    }

    /**
     * Метод для поиска волонтёра по никнейму
     *
     * @param nickName
     * @return
     */
    @Override
    public Volunteers getByNickName(String nickName) {
        return volunteerRepository.findVolunteersByNickName(nickName);
//        return findById(0L);
    }
}
