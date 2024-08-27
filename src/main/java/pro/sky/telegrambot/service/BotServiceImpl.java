package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;
import pro.sky.telegrambot.interfaces.BotService;

@Service
public class BotServiceImpl implements BotService {
    private final AnswerService answerService;

    public BotServiceImpl(AnswerService answerService) {
        this.answerService = answerService;
    }

    /**
     * Метод проверки сообщений от пользователя и ответа на них.
     * Пока через if.
     *
     * @param update
     * @return Метод ответа
     */
    @Override
    public SendMessage check(Update update) {
        if (update.message().text().equals("/start")) {
            return answerService.welcome(update);
        } else if (update.message().text().equals("/infoshelter")) {
            return answerService.giveInfo(update);
        } else if (update.message().text().equals("/howgetanimal")) {
            return answerService.giveInfoGetAnimal(update);
        } else if (update.message().text().equals("/sendreport")) {
            return answerService.sendReport(update);
        } else if (update.message().text().equals("/callvolunteer")) {
            return answerService.callVolunteer(update);
        } else if (update.message().text().equals("/sheltordogs")) {
            return answerService.giveInfoSheltorForDogs(update);
        }
        return new SendMessage(update.message().chat().id(), "Функция не существуеют");
    }
}