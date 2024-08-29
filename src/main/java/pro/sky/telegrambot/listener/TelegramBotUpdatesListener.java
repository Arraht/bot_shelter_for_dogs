package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Answer;
import pro.sky.telegrambot.interfaces.BotService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Map<Long, Answer> answerMap = new HashMap<>(Map.of());
    private SendResponse response;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private Answer answer;
    private Long chatIdOne;
    private String commandOne;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private BotService botService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            try {
                response = telegramBot.execute(botService.check(update));
            } catch (NullPointerException e) {
                System.out.println("NullPointerException");
            }
            logger.info("Processing update: {}", update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}