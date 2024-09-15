package pro.sky.telegrambot.service.shelter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Dog;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.exception.NotFoundDogByIdException;
import pro.sky.telegrambot.exception.NotNullIdException;
import pro.sky.telegrambot.interfaces.shelter.PetService;
import pro.sky.telegrambot.repository.shelter.PetRepository;

@Service
public class DogServiceImpl implements PetService {

    /**
     * <p>репозиторий<p/>
     */
    private final PetRepository petRepository;

    private final String CLASS_PET = Dog.CLASS_PET;
    /**
     * <p>логирование работы класса<p/>
     */
    private Logger logger = LoggerFactory.getLogger(DogServiceImpl.class);
    /**
     * <p>конструктор<p/>
     */
    public DogServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Добавляет в базу данных переданную собаку
     *
     * @param dog структура собака
     * @exception NotNullIdException если в переданной собаке заполнен id
     * @return возвращает добавленную собаку
     */
    @Override
    public Pet add(Pet dog) {
        logger.info("Was invoked method add : dog={}", dog);
        if (dog.getId() != null) {
            logger.error("An error occurred because dog have id={}", dog.getId());
            throw new NotNullIdException("При создании новой собаки не должно быть указано id в переданном запросе на сервер!");
        }
        return petRepository.save(dog);
    }

    /**
     * Ищет в базе данных переданную собаку по id
     *
     * @param dog структура собака
     * @exception NotFoundDogByIdException если собака не найдена по переданному id
     * @return возвращает найденную собаку
     */
    @Override
    public Pet find(Pet dog) {
        logger.info("Was invoked method find : id={}", dog.getId());
        Dog foundedDog = (Dog) petRepository.findById(dog.getId(), CLASS_PET).orElse(null);
        if (foundedDog == null) {
            logger.error("An error occurred because dog not found by id={}", dog.getId());
            throw new NotFoundDogByIdException("Собака не найдена по ID!");
        }
        logger.debug("dog={}", dog);
        return foundedDog;
    }

    /**
     * Редактирует переданную собаку по id (полносностью заменяет ранее записанные данные на переданные)
     *
     * @param dog структура собака
     * @see  #find(Pet)
     * @return возвращает отредактированную собаку
     */
    @Override
    public Pet edit(Pet dog) {
        logger.info("Was invoked method edit : dog={}", dog);
        find(dog);
        return petRepository.save(dog);
    }

    /**
     * Ищет в базе данных переданную собаку по id и удалает объект из базы данных.
     *
     * @param dog структура собака
     * @see  #find(Pet)
     * @return возвращает удаленную собаку
     */
    @Override
    public Pet remove(Pet dog) {
        logger.info("Was invoked method remove : dog={}", dog);
        Pet foundedDog = find(dog);
        petRepository.deleteById(foundedDog.getId());
        return foundedDog;
    }

    /**
     * Ищет в базе данных переданную собаку по id и удалает объект из базы данных.
     *
     * @param dogId структура собаку
     * @throws NotFoundDogByIdException возращают ошибку если не найдет
     * @return возвращает найденный по идентификатору собаку
     */
    @Override
    public Pet findById(Long dogId) {
        logger.info("Was invoked method findById : dogId={}", dogId);
        Dog dog = (Dog) petRepository.findById(dogId, CLASS_PET).orElseThrow(() -> new NotFoundDogByIdException("Приют не найден по ID!"));
        logger.info("Was invoked method findById : dog={}", dog);
        return dog;
    }
}
