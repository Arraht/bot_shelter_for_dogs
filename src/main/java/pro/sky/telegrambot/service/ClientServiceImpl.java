package pro.sky.telegrambot.service;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.Client;
import pro.sky.telegrambot.interfaces.ClientService;
import pro.sky.telegrambot.repository.ClientRepository;

@Component
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private Client client;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private void createClient(Long id, String name, Long chatId) {
        client = new Client(id, name, chatId);
        clientRepository.save(client);
    }

    @Override
    public Client findClientByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    @Override
    public void checkClient(Long id, String name, Long chatId) {
        if (findClientByChatId(chatId) == null) {
            createClient(id, name, chatId);
        }
    }
}