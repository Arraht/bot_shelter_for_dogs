package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.interfaces.bot.CallbackQueryService;
import pro.sky.telegrambot.interfaces.bot.CommandService;

@Component
public class CallbackQueryServiceImpl implements CallbackQueryService {
    private final CommandService commandService;

    public CallbackQueryServiceImpl(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * Метод для проверки CallbackQuery на null
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage checkNullCallbackQuery(Update update) {
        if (update.callbackQuery() != null) {
            return hasCallbackQuery(update);
        } else {
            return new SendMessage(update.message().chat().id(), "Функция не существует");
        }
    }

    /**
     * Метод для проверки CallbackQuery в сообщении от клиента
     *
     * @param update
     * @return метод ответа
     */
    private SendMessage hasCallbackQuery(Update update) {
        String command = update.callbackQuery().data();
        switch (command) {
            case "SHELTER_DOGS":
                return commandService.getCommandShelterDogs(update);
            case "SHELTER":
                return commandService.getCommandShelter(update);
            case "CALL_VOL":
                return commandService.getCommandCallVol(update);
            case "REPORT":
                return commandService.getCommandReport(update);
            case "HOW_DOGS":
                return commandService.getCommandHowDogs(update);
            case "CALL_VOLUNTEER":
                return commandService.getCommandCallVolunteer(update);
            default:
                return new SendMessage(update.message().chat().id(), "");
        }
    }
}