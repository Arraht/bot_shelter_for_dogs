package pro.sky.telegrambot.service.bot;

import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.Client;
import pro.sky.telegrambot.entity.ReportFromBot;
import pro.sky.telegrambot.entity.VolunteersChatId;
import pro.sky.telegrambot.interfaces.bot.ClientService;
import pro.sky.telegrambot.interfaces.bot.VolunteersChatIdService;
import pro.sky.telegrambot.interfaces.volunteer.VolunteerService;
import pro.sky.telegrambot.repository.bot.ReportFromBotRepository;
import pro.sky.telegrambot.repository.bot.VolunteersChatIdRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class VolunteersChatIdServiceImpl implements VolunteersChatIdService {
    private final VolunteerService volunteerService;
    private final VolunteersChatIdRepository volunteersChatIdRepository;
    private final ReportFromBotRepository reportFromBotRepository;
    private final ClientService clientService;
    private Long volunteersId;
    private boolean checkParent;
    private boolean changeNotParent = false;
    private boolean complete = false;
    private LocalTime timeFocus;
    private LocalDate dateNowForTimeFocus;
    private LocalDateTime dateTimeNow;
    private LocalDateTime dateTimeFocus;
    private LocalDateTime dateTimeReport;

    public VolunteersChatIdServiceImpl(VolunteerService volunteerService,
                                       VolunteersChatIdRepository volunteersChatIdRepository,
                                       ReportFromBotRepository reportFromBotRepository,
                                       ClientService clientService) {
        this.volunteerService = volunteerService;
        this.volunteersChatIdRepository = volunteersChatIdRepository;
        this.reportFromBotRepository = reportFromBotRepository;
        this.clientService = clientService;
    }

    /**
     * Метод регистрации волонтёра
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage registerVolunteer(Update update) {
        String nickname = update.message().text().substring(update.message().text().indexOf("@"));
        if (volunteerService.getByNickName(nickname) == null) {
            return new SendMessage(update.message().chat().id(), "Неверный никнейм");
        } else if (volunteerService.getByNickName(nickname) != null && checkVolunteerRegister(nickname)) {
            return new SendMessage(update.message().chat().id(), "Вы уже зарегистрированы в боте как волонтёр");
        } else {
            VolunteersChatId volunteersChatId = new VolunteersChatId(null, volunteersId, update.message().chat().id());
            volunteersChatIdRepository.save(volunteersChatId);
            return new SendMessage(update.message().chat().id(), "Вы были зарегистрированы в боте " +
                    "как волонтёр. Теперь к вам могут поступать сообщения от клиентов");
        }
    }

    /**
     * Метод отправки обратной связи клиенту
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage giveFeedBack(Update update) {
        String nickname = update.message().text().substring(update.message().text().indexOf("@"));
        Client client = clientService.findClientByUserName(nickname);
        return new SendMessage(client.getChatId(), "Дорогой усыновитель, мы заметили, что ты заполняешь отчет " +
                "не так подробно, как необходимо.Пожалуйста, подойди ответственнее к этому занятию. В противном случае " +
                "волонтеры приюта будут обязаны самолично проверять условия содержания животного");
    }

    /**
     * Метод для проверки зарегистрированных волонтёров
     *
     * @param nickname
     * @return
     */
    private boolean checkVolunteerRegister(String nickname) {
        volunteersId = volunteerService.getByNickName(nickname).getId();
        if (volunteersChatIdRepository.findVolunteersChatIdByVolunteerId(volunteersId) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод для вызова волонтёра
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage getCallVolunteer(Update update) {
        List<VolunteersChatId> volunteersChatsId = volunteersChatIdRepository.findAll();
        for (VolunteersChatId volunteersChatId : volunteersChatsId) {
            return new SendMessage(volunteersChatId.getChatId(), "Клиент @" + update.callbackQuery()
                    .from().username() + " хочет связаться");
        }
        return new SendMessage(update.callbackQuery().message().chat().id(), "");
    }

    /**
     * Метод для отправки отчёта(фото) волонтёрам
     *
     * @param update
     * @return
     */
    @Override
    public SendPhoto getReport(Update update) {
        Integer length = update.message().photo().length - 1;
        PhotoSize photoSize = update.message().photo()[length];
        Client client = clientService.findClientByChatId(update.message().chat().id());
        String messageReport = update.message().caption();
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        byte[] data = photoSize.fileUniqueId().getBytes();
        update(client.getUserName(), messageReport, timeNow, data, client, photoSize);
        List<VolunteersChatId> volunteersChatsId = volunteersChatIdRepository.findAll();
        for (VolunteersChatId volunteersChatId : volunteersChatsId) {
            return new SendPhoto(volunteersChatId.getChatId(), photoSize.fileId());
        }
        return null;
    }

    /**
     * Метод для назначения окончания испытательного срока
     *
     * @param userName
     * @param hour
     * @param minute
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void setCompeteSendReport(String userName, Integer hour, Integer minute,
                                     Integer year, Integer month, Integer day) {
        ReportFromBot report = reportFromBotRepository.findReportFromBotByUserName(userName);
        LocalDate dateFocus = LocalDate.of(year, month, day);
        LocalTime timeFocus = LocalTime.of(hour, minute);
        LocalDateTime dateTimeFocus = LocalDateTime.of(dateFocus, timeFocus);
        report.setTimeComplete(dateTimeFocus);
        reportFromBotRepository.save(report);
    }

    /**
     * Метод для ежедневной автоматической проверки отчёта в БД ботом
     *
     * @param hour
     * @param minute
     * @return
     */
    @Override
    public SendMessage checkReportFromRepository(Integer hour, Integer minute) {
        List<VolunteersChatId> volunteersChatsId = volunteersChatIdRepository.findAll();
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            checkParent = client.isParent();
            return checkSendReportByClient(client, volunteersChatsId, hour, minute);
        }
        return null;
    }

    /**
     * Метод для проверки условия сдачи отчёта
     *
     * @param client
     * @param volunteersChatsId
     * @param hour
     * @param minute
     * @return
     */
    private SendMessage checkSendReportByClient(Client client, List<VolunteersChatId> volunteersChatsId,
                                                Integer hour, Integer minute) {
        ReportFromBot report = reportFromBotRepository.findReportFromBotByUserName(client.getUserName());
        if (!checkParent && !changeNotParent && !complete) {
            return new SendMessage(client.getChatId(), "");
        } else if (checkParent && !changeNotParent && !complete) {
            return checkSendReport(client, report, hour, minute);
        } else if (checkParent && changeNotParent && !complete) {
            for (VolunteersChatId volunteersChatId : volunteersChatsId) {
                return new SendMessage(volunteersChatId.getChatId(), "Клиент " + client.getUserName() +
                        " больше не является усыновителем, измените флаг");
            }
        } else if (complete) {
            for (VolunteersChatId volunteersChatId : volunteersChatsId) {
                return new SendMessage(volunteersChatId.getChatId(), "Клиент " + client.getUserName() +
                        " прошёл испытательный срок, измените флаг");
            }
        }
        return null;
    }

    /**
     * Метод для проверки отчёта
     *
     * @param client
     * @param report
     * @param hour
     * @param minute
     * @return
     */
    private SendMessage checkSendReport(Client client, ReportFromBot report, Integer hour, Integer minute) {
        defaultTimeComplete(client);
        timeFocus = LocalTime.of(hour, minute);
        dateNowForTimeFocus = LocalDate.now();
        dateTimeNow = LocalDateTime.now();
        dateTimeFocus = LocalDateTime.of(dateNowForTimeFocus, timeFocus);
        dateTimeReport = report.getTimeSendReport();
        boolean checkTime = dateTimeNow.truncatedTo(ChronoUnit.MINUTES)
                .equals(dateTimeFocus.truncatedTo(ChronoUnit.MINUTES));
        List<VolunteersChatId> volunteersChatsId = volunteersChatIdRepository.findAll();
        if (report.getDataReportBot() != null && dateTimeReport
                .truncatedTo(ChronoUnit.DAYS)
                .equals(dateTimeNow
                        .truncatedTo(ChronoUnit.DAYS)) && checkTime) {
            return new SendMessage(client.getChatId(), "Да");
        } else if (report.getDataReportBot() != null && dateTimeReport
                .truncatedTo(ChronoUnit.DAYS).plusDays(1L)
                .equals(dateTimeNow
                        .truncatedTo(ChronoUnit.DAYS)) && checkTime) {
            return new SendMessage(client.getChatId(), "Пожалуйста, заполните и отправьте отчёт");
        } else if (report.getDataReportBot() != null && dateTimeReport
                .truncatedTo(ChronoUnit.DAYS)
                .plusDays(2)
                .equals(dateTimeNow
                        .truncatedTo(ChronoUnit.DAYS)) && checkTime) {
            for (VolunteersChatId volunteersChatId : volunteersChatsId) {
                return new SendMessage(volunteersChatId.getChatId(), "Клиент " + client.getUserName() +
                        " не отправляет отчёт 2 дня");
            }
        } else if (report.getDataReportBot() != null && dateTimeReport
                .truncatedTo(ChronoUnit.DAYS)
                .plusDays(3)
                .equals(dateTimeNow
                        .truncatedTo(ChronoUnit.DAYS)) && checkTime) {
            changeNotParent = true;
            for (VolunteersChatId volunteersChatId : volunteersChatsId) {
                return new SendMessage(volunteersChatId.getChatId(), "Клиент " + client.getUserName() +
                        " не отправляет отчёт 3 дня и больше не является усыновителем, просьба забрать животное" +
                        " обратно в приют");
            }
        } else if (report.getTimeComplete() != null && dateTimeReport
                .truncatedTo(ChronoUnit.DAYS)
                .equals(report
                        .getTimeComplete()
                        .truncatedTo(ChronoUnit.DAYS)) && checkTime) {
            complete = true;
            return new SendMessage(client.getChatId(), "Испытательный срок завершен");
        }
        return new SendMessage(client.getChatId(), "");
    }

    /**
     * Метод для установки значения по умолчанию испытательного срока
     *
     * @param client
     */
    private void defaultTimeComplete(Client client) {
        ReportFromBot report = reportFromBotRepository.findReportFromBotByUserName(client.getUserName());
        if (report.getTimeComplete() == null) {
            LocalDateTime defaultTimeComplete = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(30);
            report.setTimeComplete(defaultTimeComplete);
            reportFromBotRepository.save(report);
        }
    }

    /**
     * Метод для сохранения репорта в БД
     *
     * @param client
     * @param photoSize
     */
    private void saveReport(Client client, PhotoSize photoSize,
                            String messageReport, LocalDateTime localDateTime, byte[] data) {
        ReportFromBot reportFromBot = new ReportFromBot();
        reportFromBot.setFileSize(photoSize.fileSize());
        reportFromBot.setFileId(photoSize.fileId());
        reportFromBot.setUserName(client.getUserName());
        reportFromBot.setChatId(client.getChatId());
        reportFromBot.setDataReportBot(data);
        reportFromBot.setMessageReportFromBot(messageReport);
        reportFromBot.setTimeSendReport(localDateTime);
        reportFromBotRepository.save(reportFromBot);
    }

    /**
     * Метод обновления отчёта в БД
     *
     * @param userName
     * @param messageReport
     * @param localDateTime
     * @param data
     * @param client
     * @param photoSize
     */
    private void update(String userName, String messageReport, LocalDateTime localDateTime,
                        byte[] data, Client client, PhotoSize photoSize) {
        ReportFromBot report = reportFromBotRepository.findReportFromBotByUserName(userName);
        if (report != null) {
            report.setId(reportFromBotRepository.findReportFromBotByUserName(userName).getId());
            report.setFileSize(reportFromBotRepository.findReportFromBotByUserName(userName).getFileSize());
            report.setUserName(reportFromBotRepository.findReportFromBotByUserName(userName).getUserName());
            report.setChatId(reportFromBotRepository.findReportFromBotByUserName(userName).getChatId());
            report.setDataReportBot(data);
            report.setMessageReportFromBot(messageReport);
            report.setTimeSendReport(localDateTime);
            reportFromBotRepository.save(report);
        } else {
            saveReport(client, photoSize, messageReport, localDateTime, data);
        }
    }

    /**
     * Метод для отправки отчёта(текст) волонтёрам
     *
     * @param update
     * @return
     */
    @Override
    public SendMessage textReport(Update update) {
        List<VolunteersChatId> volunteersChatsId = volunteersChatIdRepository.findAll();
        for (VolunteersChatId volunteersChatId : volunteersChatsId) {
            return new SendMessage(volunteersChatId.getChatId(), "Отчёт клиента @" + update
                    .message().chat().username() + "\n" + update.message().caption());
        }
        return new SendMessage(update.message().chat().id(), "");
    }
}