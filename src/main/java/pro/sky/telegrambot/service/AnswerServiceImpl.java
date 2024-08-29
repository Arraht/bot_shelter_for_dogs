package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {
    /**
     * Метод приветствия
     *
     * @param update
     * @return SendMessage
     */
    @Override
    public SendMessage welcome(Update update) {
        SendMessage message = new SendMessage(update.message().chat().id(), "Welcome! Пожалусйта выберите приют:\n" +
                "/sheltordogs - приют для собак\n" + "/sheltorcat - приют для кошек");
        return message;
    }

    /**
     * Метод для вывода информации о приюте
     *
     * @param update
     * @return SendMessage
     */
    @Override
    public SendMessage giveInfo(Update update) {
        return new SendMessage(update.message().chat().id(), "Здесь будет инфо о приюте для собак");
    }

    /**
     * Метод для вывода инфо о том как взять животное
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage giveInfoGetAnimal(Update update) {
        return new SendMessage(update.message().chat().id(), "Здесь будет инфо о том, как взять собаку");
    }

    /**
     * Метод для отправки отчёта о питомце.
     * Пока намётки
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage sendReport(Update update) {
        return new SendMessage(update.message().chat().id(), "Отправить отчёт о питомце!");
    }

    /**
     * Метод для вызова волонтёра
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage callVolunteer(Update update) {
        return new SendMessage(update.message().chat().id(), "Здесь будет вызов волонтёра!");
    }

    /**
     * Метод для выбора действия прияюта для собак
     * @param update
     * @return
     */
    @Override
    public SendMessage giveInfoSheltorForDogs(Update update) {
        return new SendMessage(update.message().chat().id(), "Приют для собак. Что надо сделать:\n" +
                "/infoshelterdogs - информация о приюте\n" +
                "/howgetanimaldogs - как взять животное\n"+
                "/sendreport - отослать отчёт\n" +
                "/callvolunteer - позвать волонтёра");
    }
}