package pro.sky.telegrambot.interfaces.volunteer;

import pro.sky.telegrambot.entity.Volunteer;

/**
 * <p>интерфейс "обслуживающий волонтеров"</p>
 *
 * @Author manxix69
 */
public interface VolunteerService {
    Volunteer add(Volunteer volunteer);
    Volunteer find(Volunteer volunteer);
    Volunteer edit(Volunteer volunteer);
    Volunteer remove(Volunteer volunteer);
    Volunteer findById(Long volunteerId);
}
