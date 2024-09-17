package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Client;
import pro.sky.telegrambot.interfaces.bot.*;

import java.util.List;

@Service
public class BotServiceImpl implements BotService {
    private final CommandService commandService;
    private final CallbackQueryService callbackQueryService;
    private final ClientService clientService;

    public BotServiceImpl(CommandService commandService, CallbackQueryService callbackQueryService,
                          ClientService clientService) {
        this.commandService = commandService;
        this.callbackQueryService = callbackQueryService;
        this.clientService = clientService;
    }

    /**
     * Метод проверки сообщений от пользователя и ответа на них.
     *
     * @param update
     * @return Метод ответа
     */
    @Override
    public SendMessage check(Update update) {
        boolean messageNotNull = update.message() != null && update.message().text() != null
                && update.message().photo() == null;
        boolean callbackQueryNull = update.callbackQuery() == null;
        if (messageNotNull && update.message().text().equals("/start")) {
            return commandService.getStart(update);
        } else if (messageNotNull && update.message().text().equals("/adminShelterVolunteer")) {
            return commandService.getCommandAdmin(update);
        } else if (messageNotNull && update.message().text().contains("/nicknameVolunteersAdminShelter")) {
            return commandService.checkNickNameRegister(update);
        } else if (messageNotNull && update.message().text().contains("/feedback")) {
            return commandService.getCommandFeedBack(update);
        } else if (callbackQueryNull && update.message().photo() != null && update.message().caption() == null) {
            return commandService.getWarningSendReportNotText(update);
        } else if (callbackQueryNull && messageNotNull && commandService.checkCommand(update.message().chat().id())) {
            return commandService.getWarningSendReportNotPhoto(update);
        } else if (!callbackQueryNull) {
            return callbackQueryService.checkNullCallbackQuery(update);
        } else if (commandService.checkCommandClient(update) && update.message() != null
                && update.message().text() != null) {
            return commandService.writeContactClient(update);
        }
        return new SendMessage(update.message().chat().id(), "Функция не существует");
    }

    /**
     * Метод для проверки отчета в БД из прослушивателя
     *
     * @return
     */
    @Override
    public SendMessage checkReportBot() {
        return commandService.commandCheckReportFromRepository(21, 0);
    }

    /**
     * Метод отправки изображений
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto photo(Update update) {
        return callbackQueryService.hasCallbackQueryPhoto(update);
    }

    /**
     * Метод для отправки отчёта(фото)
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto report(Update update) {
        return commandService.getReport(update);
    }

    /**
     * Метод для отправки отчета(текст)
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage reportText(Update update) {
        return commandService.reportTextByVolunteer(update);
    }

    /**
     * Метод для проверки отчёта от клиента для пересылки волонтёрам
     *
     * @param update
     * @return
     */
    @Override
    public Boolean checkReport(Update update) {
        if (update.callbackQuery() == null && update.message().photo() != null && update.message().caption() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод для проверки условия callbackQuery
     *
     * @param update
     * @return true или false
     */
    @Override
    public Boolean checkPhoto(Update update) {
        if (update.message() == null) {
            String command = update.callbackQuery().data();
            switch (command) {
                case "DRIVE":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}