package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import pro.sky.telegrambot.entity.PhotoForReport;

public interface VolunteersChatIdService {

    SendMessage registerVolunteer(Update update);

    SendMessage getCallVolunteer(Update update);

    SendPhoto getReport(Update update);

    SendMessage textReport(Update update);
}