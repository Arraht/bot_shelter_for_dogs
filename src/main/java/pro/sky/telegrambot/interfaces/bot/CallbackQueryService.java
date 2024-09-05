package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CallbackQueryService {

    SendMessage checkNullCallbackQuery(Update update);
}