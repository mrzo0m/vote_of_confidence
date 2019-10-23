package io.voteofconf.communications.messangers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info(update.getMessage().getText());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage() && update.getMessage().hasText()) {
                log.info(update.getMessage().getText());
            }
        });
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "voc_history_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "1010956327:AAHxR5M3bB8eeOG0xhTME2HconXCSg8ne9I";
    }
}