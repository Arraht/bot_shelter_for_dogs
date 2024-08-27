package pro.sky.telegrambot.interfaces;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface AnswerService {

    SendMessage welcome(Update update);

    SendMessage giveInfo(Update update);

    SendMessage giveInfoGetAnimal(Update update);

    SendMessage sendReport(Update update);

    SendMessage callVolunteer(Update update);
}