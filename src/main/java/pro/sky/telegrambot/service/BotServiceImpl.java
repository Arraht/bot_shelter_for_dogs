package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;
import pro.sky.telegrambot.interfaces.BotService;

@Service
public class BotServiceImpl implements BotService {
    private Long chatId;
    private Integer messageId;
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
        if (update.message() != null && update.message().text().equals("/start")) {
            chatId = update.message().chat().id();
            messageId = update.message().messageId();
            return answerService.welcome(chatId);
        } else if (update.callbackQuery().data().equals("SHELTER_DOGS")) {
            return answerService.giveInfo(chatId);
        } else if (update.callbackQuery().data().equals("SHELTER")) {
            return answerService.welcome(chatId);
        } else if (update.callbackQuery().data().equals("CALL_VOL")) {
            return answerService.callVolunteer(chatId);
        } else if (update.callbackQuery().data().equals("REPORT")) {
            return answerService.sendReport(chatId);
        } else if (update.callbackQuery().data().equals("HOW_DOGS")) {
            return answerService.giveInfoGetAnimal(chatId);
        }
        return new SendMessage(chatId, "Функция не существуеют");
    }
}