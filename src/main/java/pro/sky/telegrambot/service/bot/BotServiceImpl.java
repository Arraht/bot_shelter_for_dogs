package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.bot.*;

@Service
public class BotServiceImpl implements BotService {
    private final CommandService commandService;
    private final CallbackQueryService callbackQueryService;

    public BotServiceImpl(CommandService commandService, CallbackQueryService callbackQueryService) {
        this.commandService = commandService;
        this.callbackQueryService = callbackQueryService;
    }

    /**
     * Метод проверки сообщений от пользователя и ответа на них.
     *
     * @param update
     * @return Метод ответа
     */
    @Override
    public SendMessage check(Update update) {
        if (update.message() != null && update.message().text().equals("/start")) {
            return commandService.getStart(update);
        } else if (update.message() != null && update.message().text().equals("/adminShelterVolunteer")) {
            return commandService.getCommandAdmin(update);
        } else if (update.message() != null
                && update.message().text().contains("/nicknameVolunteersAdminShelter")) {
            return commandService.checkNickNameRegister(update);
        }
        return callbackQueryService.checkNullCallbackQuery(update);
    }
}