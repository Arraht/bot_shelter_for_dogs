package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.io.IOException;

public interface CommandService {

    boolean checkCommand(Long chatId);

    SendMessage getStart(Update update);

    SendMessage writeContactClient(Update update);

    Boolean checkCommandClient(Update update);

    SendMessage sendMessageCommandContact(Update update);

    SendMessage checkNickNameRegister(Update update);

    SendMessage getCommandAdmin(Update update);

    SendMessage getCommandSecurity(Update update);

    SendMessage getCommandSave(Update update);

    SendMessage getCommandPlace(Update update);

    SendMessage getCommandWorTime(Update update);

    SendMessage getCommandShelterDogs(Update update);

    SendMessage getCommandShelter(Update update);

    SendMessage getCommandCallVol(Update update);

    SendMessage getCommandCallVolunteer(Update update);

    SendPhoto getReport(Update update);

    SendMessage reportTextByVolunteer(Update update);

    SendMessage getCommandReport(Update update);

    SendMessage getCommandSendReport(Update update);

    SendMessage getWarningSendReportNotText(Update update);

    SendMessage getWarningSendReportNotPhoto(Update update);

    SendMessage getCommandHowDogs(Update update);

    SendPhoto getCommandDrive(Update update) ;

    SendMessage getCommandListDogs(Update update);

    SendMessage getCommandListDocs(Update update);

    SendMessage getCommandRoles(Update update);

    SendMessage getCommandDogsToHome(Update update);

    SendMessage getCommandInfoHome(Update update);

    SendMessage getCommandInfoHomeRolesPuppy(Update update);

    SendMessage getCommandAdultDogs(Update update);

    SendMessage getCommandLimitDogs(Update update);

    SendMessage getCommandDogHandler(Update update);

    SendMessage getCommandSaveDogHandler(Update update);

    SendMessage getCommandListDeny(Update update);
}