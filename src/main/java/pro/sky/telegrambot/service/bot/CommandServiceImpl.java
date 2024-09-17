package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.Answer;
import pro.sky.telegrambot.interfaces.bot.AnswerService;
import pro.sky.telegrambot.interfaces.bot.ClientService;
import pro.sky.telegrambot.interfaces.bot.CommandService;
import pro.sky.telegrambot.interfaces.bot.VolunteersChatIdService;

@Component
public class CommandServiceImpl implements CommandService {
    private final ClientService clientService;
    private final AnswerService answerService;
    private final VolunteersChatIdService volunteersChatIdService;

    public CommandServiceImpl(ClientService clientService, AnswerService answerService,
                              VolunteersChatIdService volunteersChatIdService) {
        this.clientService = clientService;
        this.answerService = answerService;
        this.volunteersChatIdService = volunteersChatIdService;
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
     * Метод для проверки последней команды для отправки отчёта
     *
     * @param chatId
     * @return
     */
    @Override
    public boolean checkCommand(Long chatId) {
        Answer answer = answerService.findAnswerByChatId(chatId);
        String command = answer.getCommand();
        if (command.equals("SEND_REPORT")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод для установки испытательного срока волонтёром
     *
     * @param userName
     * @param hour
     * @param minute
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void commandSetCompleteReport(String userName, Integer hour, Integer minute,
                                         Integer year, Integer month, Integer day) {
        volunteersChatIdService.setCompeteSendReport(userName, hour, minute, year, month, day);
    }

    /**
     * Метод проверки отчёта из БД для бот сервиса
     *
     * @param hour
     * @param minute
     * @return
     */
    @Override
    public SendMessage commandCheckReportFromRepository(Integer hour, Integer minute) {
        return volunteersChatIdService.checkReportFromRepository(hour, minute);
    }

    /**
     * Метод для команды /start
     *
     * @param update
     * @return answerService.welcome(update.message ().chat().id())
     */
    @Override
    public SendMessage getStart(Update update) {
        clientService.checkClient(null, update.message().chat().firstName(), update.message().chat().id(),
                update.message().chat().username());
        check(update.message().chat().id(), update.message().text());
        return answerService.welcome(update.message().chat().id());
    }

    /**
     * Метод для сообщения клиенту об неправильном номере, что он ввёл
     *
     * @param chatId
     * @return
     */
    private SendMessage wrongInput(Long chatId) {
        return new SendMessage(chatId, "Вы ввели что-то не верно");
    }

    /**
     * Метод для записи номера телефона клиента
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage writeContactClient(Update update) {
        String inputUser = update.message().text();
        if (inputUser.matches("[0-9]+") && inputUser.length() == 11) {
            Long contact = Long.valueOf(update.message().text());
            clientService.createContactClient(update.message().chat().id(), contact);
            return answerService.setClientContact(update.message().chat().id());
        } else {
            wrongInput(update.message().chat().id());
            return wrongInput(update.message().chat().id());
        }
    }

    /**
     * Метод проверки последней команды клиента
     *
     * @param update
     * @return
     */
    @Override
    public Boolean checkCommandClient(Update update) {
        Answer foundAnswer = answerService.findAnswerByChatId(update.message().chat().id());
        if (foundAnswer.getCommand().equals("CONTACT")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод для команды CONTACT
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage sendMessageCommandContact(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.sendMessageSetContact(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для проверки никнейма волонтёра при регистрации в боте через
     * команду /nicknameVolunteersAdminShelter:
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage checkNickNameRegister(Update update) {
        return volunteersChatIdService.registerVolunteer(update);
    }

    /**
     * Метод исполнения для дачи обратной связи
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandFeedBack(Update update) {
        return volunteersChatIdService.giveFeedBack(update);
    }

    /**
     * Метод для регистрации chat_id волонтёра через команду /adminShelterVolunteer
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandAdmin(Update update) {
        return new SendMessage(update.message().chat().id(), "Введите свой никнейм, который указывали " +
                "при регистрации, с помощью команды /nicknameVolunteersAdminShelter через @ без пробелов(пример : " +
                "/nicknameVolunteersAdminShelter@Gray)");
    }

    /**
     * Метод для команды SECURITY
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandSecurity(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoSecurity(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды SAVE
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandSave(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoSave(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды PLACE
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandPlace(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoPlaceShelterDogs(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды WORK_TIME
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandWorTime(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoWorkTime(update.callbackQuery().message().chat().id());
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
     * Метод для связи с волонтёрами для команды CALL_VOLUNTEER
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandCallVolunteer(Update update) {
        return volunteersChatIdService.getCallVolunteer(update);
    }

    /**
     * Метод для отправки отчёта(фото) волонтёрам
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto getReport(Update update) {
        return volunteersChatIdService.getReport(update);
    }

    /**
     * Метод для отправки отчёта(текст) волонтёрам
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage reportTextByVolunteer(Update update) {
        return volunteersChatIdService.textReport(update);
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
     * Метод для команды SEND_REPORT
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandSendReport(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoSendReport(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для напоминания, что должно быть в отчёте если нет текста
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getWarningSendReportNotText(Update update) {
        return answerService.warningNotText(update.message().chat().id());
    }

    /**
     * Метод для напоминания, что должно быть в отчёте если нет фото
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getWarningSendReportNotPhoto(Update update) {
        return answerService.warningNotPhoto(update.message().chat().id());
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

    /**
     * Метод для команды DRIVE
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto getCommandDrive(Update update) {
        return answerService.givPhoto(update);
    }

    /**
     * Метод для команды LIST_DOGS
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandListDogs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoListDogs(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды LIST_DOCS
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandListDocs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoDocuments(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды ROLES
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandRoles(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoListRules(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды DOGS_TO_HOME
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandDogsToHome(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoTransportDogsToHome(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды INFO_HOME
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandInfoHome(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoHomeRoles(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды PUPPY
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandInfoHomeRolesPuppy(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoHomeRolesPuppy(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды ADULT_DOG
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandAdultDogs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoHomeRolesAdult(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды LIMIT_DOG
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandLimitDogs(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoHomeRulesLimit(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды DOG_HANDLER
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandDogHandler(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoAdviceDogHandler(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды SAVE_DOG_HANDLER
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandSaveDogHandler(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoSaveDogHandler(update.callbackQuery().message().chat().id());
    }

    /**
     * Метод для команды LIST_DENY
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCommandListDeny(Update update) {
        check(update.callbackQuery().message().chat().id(), update.callbackQuery().data());
        return answerService.giveInfoListDeny(update.callbackQuery().message().chat().id());
    }
}