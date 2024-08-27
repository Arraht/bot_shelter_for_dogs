package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;
import pro.sky.telegrambot.interfaces.BotService;

import javax.persistence.Id;

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
     * @param message
     * @return Метод ответа
     */
    @Override
    public SendMessage check(Update update, String message) {
        if (message.equals("/start")) {
            return answerService.welcome(update);
        } else if (message.equals("/infoshelter")) {
            return answerService.giveInfo(update);
        } else if (message.equals("/howgetanimal")) {
            return answerService.giveInfoGetAnimal(update);
        } else if (message.equals("/sendreport")) {
            return answerService.sendReport(update);
        } else if (message.equals("/callvolunteer")) {
            return answerService.callVolunteer(update);
        }
        return new SendMessage(update.message().chat().id(), "Функция не существуеют");
    }
}