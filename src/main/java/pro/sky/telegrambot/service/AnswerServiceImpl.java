package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {
    private InlineKeyboardButton createButton(String textButton, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(textButton);
        button.setCallbackData(callbackData);
        return button;
    }

    private InlineKeyboardMarkup createMarkupInline(InlineKeyboardButton... button) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        return markup.addRow(button);
    }

    /**
     * Метод приетствия
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage welcome(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Добро пожаловать! Пожалуйста выберите интересующие Вас действия.");
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
        SendMessage message = new SendMessage(chatId, "Здесь будет инфо о приюте для собак");
        message.replyMarkup(createMarkupInline(
                createButton("Как взять собаку", "HOW_DOGS"),
                createButton("Отчёт", "REPORT"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приюты", "SHELTER")));
        return message;
    }

    /**
     * Метод для вывода инфо о том как взять животное
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage giveInfoGetAnimal(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Здесь будет инфо о том, как взять собаку");
        message.replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS")));
        return message;
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
     * Метод для вызова волонтёра
     *
     * @param chatId
     * @return
     */
    @Override
    public SendMessage callVolunteer(Long chatId) {
        SendMessage message = new SendMessage(chatId, "Здесь будет вызов волонтёра!");
        message.replyMarkup(createMarkupInline(createButton("Приюты", "SHELTER")));
        return message;
    }
}