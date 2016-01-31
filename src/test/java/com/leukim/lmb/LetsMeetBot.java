package com.leukim.lmb;

import com.leukim.lmb.commands.*;
import com.leukim.lmb.database.EventDatabase;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.IOException;

/**
 * Telegram bot that provides an event management system for groups.
 *
 * Created by miquel on 30/01/16.
 */
public class LetsMeetBot extends TelegramLongPollingBot {

    private EventDatabase database;

    public LetsMeetBot(String filePath) throws IOException {
        this.database = new EventDatabase(filePath);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                try {
                    handleCommand(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "<botname>";
    }

    @Override
    public String getBotToken() {
        return "<token>";
    }

    private void handleCommand(Message message) throws TelegramApiException {
        Command command = CommandBuilder.make(database, message);
        SendMessage reply = command.execute();
        if (reply != null) {
            sendMessage(reply);
        }
    }
}
