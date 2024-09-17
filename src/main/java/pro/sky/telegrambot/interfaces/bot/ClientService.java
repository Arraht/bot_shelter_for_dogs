package pro.sky.telegrambot.interfaces.bot;

import pro.sky.telegrambot.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client findClientByUserName(String userName);

    void createContactClient(Long chatId, Long contact);

    Client findClientByChatId(Long chatId);

    void checkClient(Long id, String name, Long chatId, String userName);
}