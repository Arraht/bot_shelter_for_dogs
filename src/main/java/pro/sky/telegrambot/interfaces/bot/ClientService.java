package pro.sky.telegrambot.interfaces.bot;

import pro.sky.telegrambot.entity.Client;

public interface ClientService {

    void createContactClient(Long chatId, Long contact);

    Client findClientByChatId(Long chatId);

    void checkClient(Long id, String name, Long chatId);
}