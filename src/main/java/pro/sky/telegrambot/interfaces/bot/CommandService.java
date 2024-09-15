package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CommandService {

    SendMessage getStart(Update update);

    SendMessage checkNickNameRegister(Update update);

    SendMessage getCommandAdmin(Update update);

    SendMessage getCommandShelterDogs(Update update);

    SendMessage getCommandShelter(Update update);

    SendMessage getCommandCallVol(Update update);

    SendMessage getCommandCallVolunteer(Update update);

    SendMessage getCommandReport(Update update);

    SendMessage getCommandHowDogs(Update update);
}