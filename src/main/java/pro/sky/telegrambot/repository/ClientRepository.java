package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByChatId(Long chatId);
}