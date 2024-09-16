package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.io.IOException;

public interface BotService {
    SendMessage check(Update update);

    SendPhoto photo(Update update);

    SendPhoto report(Update update);

    SendMessage reportText(Update update);

    Boolean checkReport(Update update);

    Boolean checkPhoto(Update update);
}