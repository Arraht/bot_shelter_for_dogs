package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
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
            return new SendMessage(update.message().chat().id(), "");
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
            case "SEND_REPORT":
                return commandService.getCommandSendReport(update);
            case "HOW_DOGS":
                return commandService.getCommandHowDogs(update);
            case "CALL_VOLUNTEER":
                return commandService.getCommandCallVolunteer(update);
            case "WORK_TIME":
                return commandService.getCommandWorTime(update);
            case "PLACE":
                return commandService.getCommandPlace(update);
            case "SECURITY":
                return commandService.getCommandSecurity(update);
            case "SAVE":
                return commandService.getCommandSave(update);
            case "CONTACT":
                return commandService.sendMessageCommandContact(update);
            case "LIST_DOGS":
                return commandService.getCommandListDogs(update);
            case "LIST_DOCS":
                return commandService.getCommandListDocs(update);
            case "ROLES":
                return commandService.getCommandRoles(update);
            case "DOGS_TO_HOME":
                return commandService.getCommandDogsToHome(update);
            case "INFO_HOME":
                return commandService.getCommandInfoHome(update);
            case "PUPPY":
                return commandService.getCommandInfoHomeRolesPuppy(update);
            case "ADULT_DOG":
                return commandService.getCommandAdultDogs(update);
            case "LIMIT_DOG":
                return commandService.getCommandLimitDogs(update);
            case "DOG_HANDLER":
                return commandService.getCommandDogHandler(update);
            case "SAVE_DOG_HANDLER":
                return commandService.getCommandSaveDogHandler(update);
            case "LIST_DENY":
                return commandService.getCommandListDeny(update);
            default:
                return new SendMessage(update.message().chat().id(), "Функция не существует");
        }
    }

    /**
     * Метод для проверки CallbackQuery для изображения в сообщении от клиента
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto hasCallbackQueryPhoto(Update update) {
        String command = update.callbackQuery().data();
        switch (command) {
            case "DRIVE":
                return commandService.getCommandDrive(update);
            default:
                return null;
        }
    }
}