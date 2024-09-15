package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.VolunteersChatId;
import pro.sky.telegrambot.interfaces.bot.VolunteersChatIdService;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.bot.VolunteersChatIdRepository;

@Component
public class VolunteersChatIdServiceImpl implements VolunteersChatIdService {
    private final VolunteerService volunteerService;
    private final VolunteersChatIdRepository volunteersChatIdRepository;
    private Long volunteersId;

    public VolunteersChatIdServiceImpl(VolunteerService volunteerService, VolunteersChatIdRepository volunteersChatIdRepository) {
        this.volunteerService = volunteerService;
        this.volunteersChatIdRepository = volunteersChatIdRepository;
    }

    /**
     * Метод регистрации волонтёра
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage registerVolunteer(Update update) {
        String nickname = update.message().text().substring(update.message().text().indexOf("@"));
        if (volunteerService.getByNickName(nickname) == null) {
            return new SendMessage(update.message().chat().id(), "Неверный никнейм");
        } else if (volunteerService.getByNickName(nickname) != null && checkTest(nickname)) {
            return new SendMessage(update.message().chat().id(), "Вы уже зарегистрированы в боте как волонтёр");
        } else {
            VolunteersChatId volunteersChatId = new VolunteersChatId(null, volunteersId, update.message().chat().id());
            volunteersChatIdRepository.save(volunteersChatId);
            return new SendMessage(update.message().chat().id(), "Вы были зарегистрированы в боте " +
                    "как волонтёр. Теперь к вам могут поступать сообщения от клиентов");
        }
    }

    /**
     * Метод для проверки зарегистрированных волонтёров
     *
     * @param nickname
     * @return
     */
    private boolean checkTest(String nickname) {
        volunteersId = volunteerService.getByNickName(nickname).getId();
        if (volunteersChatIdRepository.findVolunteersChatIdByVolunteerId(volunteersId) != null) {
            return true;
        } else {
            return false;
        }
    }
}