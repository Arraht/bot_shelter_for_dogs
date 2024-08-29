package pro.sky.telegrambot.interfaces;

import com.pengrad.telegrambot.request.SendMessage;

public interface AnswerService {

    SendMessage welcome(Long chatId);

    SendMessage giveInfo(Long chatId);

    SendMessage giveInfoGetAnimal(Long chatId);

    SendMessage sendReport(Long chatId);

    SendMessage callVolunteer(Long chatId);
}