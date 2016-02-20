package com.leukim.lmb;

import com.leukim.lmb.state.BotStatus;
import com.leukim.lmb.state.Conversation;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Telegram bot that provides an event management system for groups.
 *
 * Created by miquel on 30/01/16.
 */
public class LetsMeetBot extends TelegramLongPollingBot {

    private final BotStatus status;

    public LetsMeetBot() {
        this.status = new BotStatus();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage reply;
            if (message.hasText()) {
                Long chatID = message.getChatId();
                Conversation conversation = status.getConversationOrNew(chatID);
                reply = conversation.process(message);
            } else {
                reply = new SendMessage();
                reply.setChatId(message.getChatId().toString());
                reply.setText("Only text messages are supported.");
            }
            try {
                sendMessage(reply);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "letsmeetbot";
    }

    @Override
    public String getBotToken() {
        return "<token>";
    }
}
