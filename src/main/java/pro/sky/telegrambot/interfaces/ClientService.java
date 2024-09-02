package pro.sky.telegrambot.interfaces;

import pro.sky.telegrambot.entity.Client;

public interface ClientService {
    Client findClientByChatId(Long chatId);

    void checkClient(Long id, String name, Long chatId);
}