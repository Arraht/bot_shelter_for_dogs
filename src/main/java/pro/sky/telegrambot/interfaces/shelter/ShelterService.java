package pro.sky.telegrambot.interfaces.shelter;

import pro.sky.telegrambot.entity.Shelter;

/**
 * <p>интерфейс "обслуживающий приют"</p>
 *
 * @Author manxix69
 */
public interface ShelterService {
    Shelter add(Shelter shelter);
    Shelter find(Shelter shelter);
    Shelter edit(Shelter shelter);
    Shelter remove(Shelter shelter);

    Shelter findById(Long shelterId);
}
