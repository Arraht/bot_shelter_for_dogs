package pro.sky.telegrambot.repository.bot;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByChatId(Long chatId);
}