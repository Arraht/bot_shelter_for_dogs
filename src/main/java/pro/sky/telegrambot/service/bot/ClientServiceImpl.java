package pro.sky.telegrambot.service.bot;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.Client;
import pro.sky.telegrambot.interfaces.bot.ClientService;
import pro.sky.telegrambot.repository.bot.ClientRepository;

@Component
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private Client client;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Метод для сохранения клиента в БД
     *
     * @param id
     * @param name
     * @param chatId
     */
    private void createClient(Long id, String name, Long chatId) {
        client = new Client(id, name, chatId);
        clientRepository.save(client);
    }

    /**
     * Метод для поиска клиента в БД
     *
     * @param chatId
     * @return
     */
    @Override
    public Client findClientByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    /**
     * Метод для проверки клиента в БД
     *
     * @param id
     * @param name
     * @param chatId
     */
    @Override
    public void checkClient(Long id, String name, Long chatId) {
        if (findClientByChatId(chatId) == null) {
            createClient(id, name, chatId);
        }
    }
}