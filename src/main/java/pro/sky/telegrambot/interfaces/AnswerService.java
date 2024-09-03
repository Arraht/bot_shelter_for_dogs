package pro.sky.telegrambot.interfaces;

import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambot.entity.Answer;

public interface AnswerService {

    void createAnswer(Long id, String command, Long chatId);

    Answer findAnswerByChatId(Long chatId);

    void editAnswer(Long chatId, String command);

    SendMessage welcome(Long chatId);

    SendMessage giveInfo(Long chatId);

    SendMessage giveInfoGetAnimal(Long chatId);

    SendMessage sendReport(Long chatId);

    SendMessage callVolunteer(Long chatId);
}