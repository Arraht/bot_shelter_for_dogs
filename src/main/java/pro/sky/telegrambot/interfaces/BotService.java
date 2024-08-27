package pro.sky.telegrambot.interfaces;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface BotService { ;
    SendMessage check(Update update);
}