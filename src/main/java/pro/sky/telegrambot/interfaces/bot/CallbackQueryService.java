package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.io.IOException;

public interface CallbackQueryService {

    SendMessage checkNullCallbackQuery(Update update);

    SendPhoto hasCallbackQueryPhoto(Update update);
}