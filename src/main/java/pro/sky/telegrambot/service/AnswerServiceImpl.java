package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.interfaces.AnswerService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class AnswerServiceImpl implements AnswerService {
    private File infoShelter;
    private String info;
    @Value("${path.to.info.shelter.dogs}")
    private String infoShelterDogsPath;
    private Scanner scanner;
    private SendMessage message;

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
        SendMessage message = new SendMessage(chatId, "Welcome! Пожалусйта выберите приют");
        message.replyMarkup(createMarkupInline(
                createButton("Приют для собак", "SHELTER_DOGS"),
                createButton("Приют для кошек", "SHELTER_CATS")));
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
        infoShelter = new File(infoShelterDogsPath);
        return readMessage(chatId, infoShelter, infoShelterDogsPath).replyMarkup(createMarkupInline(
                createButton("Как взять собаку", "HOW_DOGS"),
                createButton("Отчёт", "REPORT"))
                .addRow(createButton("Позвать волонтёра", "CALL_VOL"),
                        createButton("Приюты", "SHELTER")));
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