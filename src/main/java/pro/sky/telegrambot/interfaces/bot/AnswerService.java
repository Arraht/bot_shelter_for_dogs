package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import pro.sky.telegrambot.entity.Answer;

import java.io.IOException;

public interface AnswerService {

    void createAnswer(Long id, String command, Long chatId);

    Answer findAnswerByChatId(Long chatId);

    void editAnswer(Long chatId, String command);

    SendMessage welcome(Long chatId);

    SendMessage giveInfo(Long chatId);

    SendMessage setClientContact(Long chatId);

    SendMessage sendMessageSetContact(Long chatId);

    SendPhoto givPhoto(Update update);

    SendMessage giveInfoGetAnimal(Long chatId);

    SendMessage giveInfoListDeny(Long chatId);

    SendMessage giveInfoAdviceDogHandler(Long chatId);

    SendMessage giveInfoSaveDogHandler(Long chatId);

    SendMessage giveInfoHomeRoles(Long chatId);

    SendMessage giveInfoHomeRulesLimit(Long chatId);

    SendMessage giveInfoHomeRolesAdult(Long chatId);

    SendMessage giveInfoHomeRolesPuppy(Long chatId);

    SendMessage giveInfoListDogs(Long chatId);

    SendMessage giveInfoListRules(Long chatId);

    SendMessage giveInfoDocuments(Long chatId);

    SendMessage giveInfoTransportDogsToHome(Long chatId);

    SendMessage giveInfoSave(Long chatId);

    SendMessage giveInfoPlaceShelterDogs(Long chatId);

    SendMessage giveInfoSecurity(Long chatId);

    SendMessage giveInfoWorkTime(Long chatId);

    SendMessage sendReport(Long chatId);

    SendMessage warningNotText(Long chatId);

    SendMessage warningNotPhoto(Long chatId);

    SendMessage giveInfoSendReport(Long chaId);

    SendMessage callVolunteer(Long chatId);
}