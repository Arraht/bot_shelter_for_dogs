package pro.sky.telegrambot.interfaces.volunteer;

import pro.sky.telegrambot.entity.Volunteers;

/**
 * <p>интерфейс "обслуживающий волонтеров"</p>
 *
 * @Author manxix69
 */
public interface VolunteerService {
    Volunteers add(Volunteers volunteers);

    Volunteers find(Volunteers volunteers);

    Volunteers edit(Volunteers volunteers);

    Volunteers remove(Volunteers volunteers);

    Volunteers findById(Long volunteerId);

    /**
     * Метод для поиска волонтёра по никнейму
     *
     * @param nickName
     * @return
     */
    Volunteers getByNickName(String nickName);
}