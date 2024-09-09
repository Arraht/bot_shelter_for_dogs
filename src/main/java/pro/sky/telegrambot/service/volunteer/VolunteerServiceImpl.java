package pro.sky.telegrambot.service.volunteer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Shelter;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.exception.NotFoundShelterByIdException;
import pro.sky.telegrambot.exception.NotFoundVolunteerByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.shelter.ShelterRepository;
import pro.sky.telegrambot.repository.volunteer.VolunteerRepository;
import pro.sky.telegrambot.service.shelter.ShelterServiceImpl;

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
     * @param volunteer структура волонтер
     * @exception NotNullIdException если в переданном объекте заполнен id
     * @return возвращает добавленного волонтера
     */
    @Override
    public Volunteer add(Volunteer volunteer) {
        logger.info("Was invoked method add : volunteer={}", volunteer);
        if (volunteer.getId() != null) {
            logger.error("An error occurred because object " + volunteer.getClass() + " have id={}", volunteer.getId());
            throw new NotNullIdException("При создании нового объекта " + volunteer + " не должно быть указано id в переданном запросе на сервер!");
        }
        return volunteerRepository.save(volunteer);
    }

    /**
     * Ищет в базе данных переданного волотера по id
     *
     * @param volunteer структура волонтер
     * @exception NotFoundVolunteerByIdException если волонтер не найден по переданному id
     * @return возвращает найденного волонтера
     */
    @Override
    public Volunteer find(Volunteer volunteer) {
        logger.info("Was invoked method find : id={}", volunteer.getId());
        Volunteer foundedVolunteer = volunteerRepository.findById(volunteer.getId()).orElse(null);
        if (foundedVolunteer == null) {
            logger.error("An error occurred because object " + volunteer.getClass() + " not found by id={}", volunteer.getId());
            throw new NotFoundVolunteerByIdException("Волонтер не найден по ID!");
        }
        logger.debug("volunteer={}", volunteer);
        return foundedVolunteer;
    }

    /**
     * Редактирует переданного волонтера по id (полносностью заменяет ранее записанные данные на переданные)
     *
     * @param volunteer структура волонтер
     * @see  #find(Volunteer)
     * @return возвращает отредактированного волонтера
     */
    @Override
    public Volunteer edit(Volunteer volunteer) {
        logger.info("Was invoked method edit : volunteer={}", volunteer);
        find(volunteer);
        return volunteerRepository.save(volunteer);
    }

    /**
     * Ищет в базе данных переданного волонтера по id и удалает объект из базы данных.
     *
     * @param volunteer структура волонтер
     * @see  #find(Volunteer)
     * @return возвращает удаленного волонтера
     */
    @Override
    public Volunteer remove(Volunteer volunteer) {
        logger.info("Was invoked method remove : volunteer={}", volunteer);
        Volunteer foundedVolunteer = find(volunteer);
        volunteerRepository.deleteById(foundedVolunteer.getId());
        return foundedVolunteer;
    }

    /**
     * Ищет в базе данных переданного волонтера по id и удалает объект из базы данных.
     *
     * @param volunteerId структура волонтер
     * @throws NotFoundVolunteerByIdException возращают ошибку если не найдет
     * @return возвращает найденный по идентификатору волонтера
     */
    @Override
    public Volunteer findById(Long volunteerId) {
        logger.info("Was invoked method findById : volunteerId={}", volunteerId);
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(() -> new NotFoundVolunteerByIdException("Волонтер не найден по ID!"));
        logger.info("Was invoked method findById : volunteer={}", volunteer);
        return volunteer;
    }
}
