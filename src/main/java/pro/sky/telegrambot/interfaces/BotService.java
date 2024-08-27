package pro.sky.telegrambot.interfaces;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface BotService {
    SendMessage check(Update update, String message);
}