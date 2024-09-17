package pro.sky.telegrambot.interfaces.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import pro.sky.telegrambot.entity.PhotoForReport;

import java.io.IOException;

public interface VolunteersChatIdService {

    SendMessage registerVolunteer(Update update);

    SendMessage giveFeedBack(Update update);

    SendMessage getCallVolunteer(Update update);

    SendPhoto getReport(Update update);

    void setCompeteSendReport(String userName, Integer hour, Integer minute,
                              Integer year, Integer month, Integer day);

    SendMessage checkReportFromRepository(Integer hour, Integer minute);

    SendMessage textReport(Update update);
}