package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Answer;
import pro.sky.telegrambot.interfaces.bot.AnswerService;
import pro.sky.telegrambot.repository.bot.AnswerRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static java.nio.channels.FileChannel.open;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    @Value("${path.to.info.shelter.dogs}")
    private String infoShelterDogsPath;
    @Value("${path.to.info.shelter.action.shelter.dogs}")
    private String infoActionSheltorDogsPath;
    @Value("${path.to.info.shelter.dogs.directions}")
    private String pathDirectionsShelterDogs;
    @Value("${path.to.info.shelter.dogs.work.time}")
    private String pathInfoWorkTime;
    @Value("${path.to.info.shelter.dogs.work.place}")
    private String pathInfoShelterDogsPlace;
    @Value("${path.to.info.shelter.dogs.work.security}")
    private String pathInfoSecurity;
    @Value("${path.to.info.shelter.dogs.work.save}")
    private String pathInfoSave;
    @Value("${path.to.info.shelter.action.dogs}")
    private String pathHelpInfo;
    @Value("${path.to.info.shelter.dogs.list.dogs}")
    private String pathListDogs;
    @Value("${path.to.info.shelter.dogs.list.documents}")
    private String pathListDocuments;
    @Value("${path.to.info.shelter.dogs.list.roles}")
    private String pathListRules;
    @Value("${path.to.info.shelter.dogs.list.transport}")
    private String pathListTransportRoles;
    @Value("${path.to.info.shelter.dogs.list.home.puppy}")
    private String pathListHomeRolesPuppy;
    @Value("${path.to.info.shelter.dogs.list.home.adult}")
    private String pathListHomeRolesAdult;
    @Value("${path.to.info.shelter.dogs.list.home.limit}")
    private String pathListHomeRulesLimit;
    @Value("${path.to.info.shelter.dogs.list.doghandler.advice}")
    private String pathListAdviceDogHandler;
    @Value("${path.to.info.shelter.dogs.list.doghandler.save}")
    private String pathListSaveDogHandler;
    @Value("${path.to.info.shelter.dogs.list.deny}")
    private String pathListDeny;
    private Scanner scanner;
    private SendMessage message;
    private Answer answer;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * Приватный метод для создания кнопок
     *
     * @param textButton
     * @param callbackData
     * @return InlineKeyboardButton
     */
    private InlineKeyboardButton createButton(String textButton, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(textButton);
        button.setCallbackData(callbackData);
        return button;
    }

    /**
     * Приватный метод для расположения кнопок в сообщении
     *
     * @param button
     * @return InlineKeyboardMarkup
     */
    private InlineKeyboardMarkup createMarkupInline(InlineKeyboardButton... button) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        return markup.addRow(button);
    }

    /**
     * Метод для волонтёров по обратной связи
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage admin(Long chatId) {
        return new SendMessage(chatId, "Обратная связь").replyMarkup(createMarkupInline(
                createButton("Дать обратную связь", "ADMIN_FEEDBACK")
        ));
    }

    /**
     * Метод для создания сущности Answer
     *
     * @param id
     * @param command
     * @param chatId
     */
    @Override
    public void createAnswer(Long id, String command, Long chatId) {
        answer = new Answer(id, command, chatId);
        answerRepository.save(answer);
    }

    /**
     * Метод для поиска сущности Answer(может вернуть null)
     *
     * @param chatId
     * @return Answer или null
     */
    @Override
    public Answer findAnswerByChatId(Long chatId) {
        if (answerRepository.findByChatId(chatId) != null) {
            return answerRepository.findByChatId(chatId);
        } else {
            return null;
        }
    }

    /**
     * Метод для изменения сущности Answer
     *
     * @param chatId
     * @param command
     */
    @Override
    public void editAnswer(Long chatId, String command) {
        Answer foundAnswerByEdit = findAnswerByChatId(chatId);
        foundAnswerByEdit.setId(foundAnswerByEdit.getId());
        foundAnswerByEdit.setChatId(foundAnswerByEdit.getChatId());
        foundAnswerByEdit.setCommand(command);
        answerRepository.save(foundAnswerByEdit);
    }

    /**
     * Метод приветствия
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage welcome(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Добро пожаловать! Пожалуйста выберите приют:");
        message.replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS"),
                createButton("Приют для кошек", "SHELTER_CATS"))
        );
        return message;
    }

    /**
     * Метод для вывода информации о приюте
     *
     * @param chatId
     * @return SendMessage
     */
    @Override
    public SendMessage giveInfo(Long chatId) {
        File infoShelter = new File(infoShelterDogsPath);
        return readMessage(chatId, infoShelter, infoShelterDogsPath).replyMarkup(createMarkupInline(
                createButton("Как взять собаку", "HOW_DOGS"),
                createButton("Отчёт", "REPORT"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приюты", "SHELTER"))
                .addRow(createButton("Расписание работы", "WORK_TIME"),
                        createButton("Адрес приюта", "PLACE"))
                .addRow(createButton("Схема проезда", "DRIVE"))
                .addRow(createButton("Оформление пропуска на машину", "SECURITY"))
                .addRow(createButton("Рекомендации о ТБ на территории приюта", "SAVE"))
                .addRow(createButton("Контактные данные для связи", "CONTACT")));
    }

    /**
     * Метод для записи телефона клиента
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage setClientContact(Long chatId) {
        return new SendMessage(chatId, "Телефон записан").replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для информирования о записи номера телефона клиента
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage sendMessageSetContact(Long chatId) {
        return new SendMessage(chatId, "Введите номер телефона");
    }

    /**
     * Метод для отправки фотографии схемы проезда к приюту
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto givPhoto(Update update) {
        File shelter = new File(pathDirectionsShelterDogs);
        return new SendPhoto(update.callbackQuery().message().chat().id(), shelter).replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вывода инфо о том как взять животное
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoGetAnimal(Long chatId) {
        File infoActionSheltorDogs = new File(infoActionSheltorDogsPath);
        return readMessage(chatId, infoActionSheltorDogs, infoActionSheltorDogsPath).replyMarkup(createMarkupInline(
                createButton("список животных для усыновления", "LIST_DOGS")
        ).addRow(createButton("рекомендации по обустройству дома", "INFO_HOME"))
                .addRow(createButton("советы кинолога по первичному общению с собакой",
                        "DOG_HANDLER"))
                .addRow(createButton("проверенные кинологи", "SAVE_DOG_HANDLER"))
                .addRow(createButton("список причин для отказа усыновления", "LIST_DENY"))
                .addRow(createButton("Контактные данные для связи", "CONTACT"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо о списке причин отказа в усыновлении собаки
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoListDeny(Long chatId) {
        File listInfoDeny = new File(pathListDeny);
        return readMessage(chatId, listInfoDeny, pathListDeny).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо по советам по первичному общению с собакой
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoAdviceDogHandler(Long chatId) {
        File listAdviceDogHandler = new File(pathListAdviceDogHandler);
        return readMessage(chatId, listAdviceDogHandler, pathListAdviceDogHandler).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо по проверенным кинологам
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoSaveDogHandler(Long chatId) {
        File listSaveDogHandler = new File(pathListSaveDogHandler);
        return readMessage(chatId, listSaveDogHandler, pathListSaveDogHandler).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова выбора собаки для получения рекомендаций по обустройству дома
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoHomeRoles(Long chatId) {
        return new SendMessage(chatId, "Выберите животное").replyMarkup(createMarkupInline(
                createButton("Щенок", "PUPPY"),
                createButton("Взрослая собака", "ADULT_DOG"))
                .addRow(createButton("C ограниченными возможностями (зрение, " +
                        "передвижение)", "LIMIT_DOG"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо об обустройстве дома для собаки с ограниченными возможностями
     * (зрение, передвижение)
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoHomeRulesLimit(Long chatId) {
        File listHomeRulesLimit = new File(pathListHomeRulesLimit);
        return readMessage(chatId, listHomeRulesLimit, pathListHomeRulesLimit).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо по обустройству дома для взрослой собаки
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoHomeRolesAdult(Long chatId) {
        File listHomeRules = new File(pathListHomeRolesPuppy);
        return readMessage(chatId, listHomeRules, pathListHomeRolesAdult).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо по обустройству дома для щенка
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoHomeRolesPuppy(Long chatId) {
        File listHomeRulesPuppy = new File(pathListHomeRolesPuppy);
        return readMessage(chatId, listHomeRulesPuppy, pathListHomeRolesPuppy).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо о списке собак для усыновления
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoListDogs(Long chatId) {
        File lstDogs = new File(pathListDogs);
        return readMessage(chatId, lstDogs, pathListDogs).replyMarkup(createMarkupInline(
                createButton("правила знакомства с животным", "ROLES"))
                .addRow(createButton("список документов для усыновления", "LIST_DOCS"))
                .addRow(createButton("рекомендации по транспортировке животного", "DOGS_TO_HOME"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо о правилах знакомства с животным
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoListRules(Long chatId) {
        File listRoles = new File(pathListRules);
        return readMessage(chatId, listRoles, pathListRules).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова инфо о списке документов для усыновления собаки
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoDocuments(Long chatId) {
        File listDocuments = new File(pathListDocuments);
        return readMessage(chatId, listDocuments, pathListDocuments).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова рекомендаций по транспортировке животного домой
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoTransportDogsToHome(Long chatId) {
        File listTransportRoles = new File(pathListTransportRoles);
        return readMessage(chatId, listTransportRoles, pathListTransportRoles).replyMarkup(createMarkupInline(
                createButton("Позвать волонтёра", "CALL_VOL"),
                createButton("Приют для собак", "SHELTER_DOGS")));
    }

    /**
     * Метод для вызова инфо о ТБ на территории приюта для собак
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoSave(Long chatId) {
        File infoSave = new File(pathInfoSave);
        return readMessage(chatId, infoSave, pathInfoSave).replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова инфо об адресе приюта
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoPlaceShelterDogs(Long chatId) {
        File infoPlace = new File(pathInfoShelterDogsPlace);
        return readMessage(chatId, infoPlace, pathInfoShelterDogsPlace).replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова инфо о контактных данных охраны
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoSecurity(Long chatId) {
        File infoSecurity = new File(pathInfoSecurity);
        return readMessage(chatId, infoSecurity, pathInfoSecurity).replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова инфо о расписании работы приютя для собак
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoWorkTime(Long chatId) {
        File infoDogsShelterWorkTime = new File(pathInfoWorkTime);
        return readMessage(chatId, infoDogsShelterWorkTime, pathInfoWorkTime).replyMarkup(
                createMarkupInline(createButton("Приют для собак", "SHELTER_DOGS"))
        );
    }

    /**
     * Метод для отправки отчёта о питомце.
     * Пока намётки
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage sendReport(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Отправить отчёт о собаке");
        message.replyMarkup(createMarkupInline(
                createButton("Отправить отчёт", "SEND_REPORT"),
                createButton("Приюты", "SHELTER")));
        return message;
    }

    /**
     * Метод для напоминания отправки отчёта, если отчёт без текста и с фотографией
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage warningNotText(Long chatId) {
        return new SendMessage(chatId, "Пожалуйста приложите текстовый отчёт к фотографии." +
                " (нажав на кнопку, посмотрите, что должно быть в отчёте)" +
                " Отправьте заново фотографии и отчёт").replyMarkup(createMarkupInline(
                createButton("Отправить отчёт", "SEND_REPORT")
        ));
    }

    /**
     * Метод для напоминания отправки отчёта, если отчёт без фотографии и с текстом
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage warningNotPhoto(Long chatId) {
        return new SendMessage(chatId, "Пожалуйста приложите фотографию к текстовому отчёту." +
                " (нажав на кнопку, посмотрите, что должно быть в отчёте)" +
                " Отправьте заново фотографии и отчёт").replyMarkup(createMarkupInline(
                createButton("Отправить отчёт", "SEND_REPORT")
        ));
    }

    /**
     * Метод для вызова инфо о том, что нужно в отчёте
     *
     * @param chaId
     * @return
     */
    @Override
    public SendMessage giveInfoSendReport(Long chaId) {
        File helpInfo = new File(pathHelpInfo);
        return readMessage(chaId, helpInfo, pathHelpInfo).replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")
        ));
    }

    /**
     * Метод для вызова волонтёра
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage callVolunteer(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Позвать волонтёра или вернуться к выбору приюта");
        message.replyMarkup(createMarkupInline(createButton("Приюты", "SHELTER"),
                createButton("Позвать волонтёра", "CALL_VOLUNTEER")));
        return message;
    }

    /**
     * Метод для чтения из файла
     *
     * @param chatId
     * @param file
     * @param path
     * @return
     */
    private SendMessage readMessage(Long chatId, File file, String path) {
        try {
            scanner = new Scanner(file);
            message = new SendMessage(chatId, Files.readAllLines(Path.of(path)).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scanner.close();
        return message;
    }
}