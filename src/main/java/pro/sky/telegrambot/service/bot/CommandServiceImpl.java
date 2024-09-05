package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.interfaces.bot.AnswerService;
import pro.sky.telegrambot.interfaces.bot.ClientService;
import pro.sky.telegrambot.interfaces.bot.CommandService;

@Component
public class CommandServiceImpl implements CommandService {
    private final ClientService clientService;
    private final AnswerService answerService;

    public CommandServiceImpl(ClientService clientService, AnswerService answerService) {
        this.clientService = clientService;
        this.answerService = answerService;
    }

    /**
     * Метод для обновления записи в БД
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

    /**
     * Метод для команды /start
     *
     * @param update
     * @return answerService.welcome(update.message ().chat().id())
     */
    @Override
    public SendMessage getStart(Update update) {
        clientService.checkClient(null, update.message().chat().firstName(), update.message().chat().id());
        check(update.message().chat().id(), update.message().text());
        return answerService.welcome(update.message().chat().id());
    }

    /**
     * Метод для команды SHELTER_DOGS
     *
     * @param update
     * @return answerService.giveInfo(update.callbackQuery ().message().chat().id())
     */
    @Override
    public SendMessage getCommandShelterDogs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfo(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды SHELTER
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandShelter(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.welcome(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды CALL_VOL
     *
     * @param update
     * @return answerService.callVolunteer(update.callbackQuery ().message().chat().id())
     */
    @Override
    public SendMessage getCommandCallVol(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.callVolunteer(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды REPORT
     *
     * @param update
     * @return answerService.sendReport(update.callbackQuery ().message().chat().id())
     */
    @Override
    public SendMessage getCommandReport(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.sendReport(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды HOW_DOGS
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandHowDogs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoGetAnimal(update.callbackQuery().message().chat().id());
    }
}