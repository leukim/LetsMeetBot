package com.leukim.lmb.commands;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.EventDatabase;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

/**
 * Base class for all classes representing a command to extend.
 * Provides some basic setup of the commonly used data in a command.
 *
 * Created by miquel on 30/01/16.
 */
public abstract class Command {

    EventDatabase database;
    List<String> params;
    SendMessage reply;

    private Message message;

    String senderID;
    String senderUsername;

    Command() {
    }

    public void set(List<String> params, Message message, SendMessage reply) {
        this.database = Services.getInstance().getDatabase();
        this.params = params;
        this.reply = reply;
        this.message = message;
        this.senderID = message.getFrom().getId().toString();
        this.senderUsername = message.getFrom().getUserName();
    }

    public abstract SendMessage execute();

    SendMessage sendMessage(String messageText) {
        reply.setText(messageText);
        return reply;
    }

    boolean hasParams(int paramNum) {
        return params.size() == paramNum;
    }
}
