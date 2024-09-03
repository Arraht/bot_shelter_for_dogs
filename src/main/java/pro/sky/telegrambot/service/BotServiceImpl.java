package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;
import pro.sky.telegrambot.interfaces.BotService;
import pro.sky.telegrambot.interfaces.ClientService;

@Service
public class BotServiceImpl implements BotService {

    private final AnswerService answerService;
    private final ClientService clientService;

    public BotServiceImpl(AnswerService answerService, ClientService clientService) {
        this.answerService = answerService;
        this.clientService = clientService;
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
            clientService.checkClient(null, update.message().chat().firstName(), update.message().chat().id());
            check(update.message().chat().id(), update.message().text());
            return answerService.welcome(update.message().chat().id());
        } else if (update.callbackQuery().data().equals("SHELTER_DOGS")) {
            check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
            return answerService.giveInfo(update.callbackQuery().message().chat().id());
        } else if (update.callbackQuery().data().equals("SHELTER")) {
            check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
            return answerService.welcome(update.callbackQuery().message().chat().id());
        } else if (update.callbackQuery().data().equals("CALL_VOL")) {
            check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
            return answerService.callVolunteer(update.callbackQuery().message().chat().id());
        } else if (update.callbackQuery().data().equals("REPORT")) {
            check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
            return answerService.sendReport(update.callbackQuery().message().chat().id());
        } else if (update.callbackQuery().data().equals("HOW_DOGS")) {
            check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
            return answerService.giveInfoGetAnimal(update.callbackQuery().message().chat().id());
        }
        return new SendMessage(update.message().chat().id(), "Функция не существуеют");
    }

    /**
     * Метод для обнвления записи в БД
     *
     * @param chatId
     * @param command
     */
    private void check(Long chatId, String command) {
        if (answerService.findAnswerByChatId(chatId) != null) {
            answerService.editAnswer(chatId, command);
        } else {
            answerService.createAnswer(null, command, chatId);
        }
    }
}