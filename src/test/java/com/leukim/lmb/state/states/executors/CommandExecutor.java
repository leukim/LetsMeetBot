package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by miquel on 03/02/16.
 */
public abstract class CommandExecutor {
    public abstract Result execute(Command command);

    protected SendMessage makeResponse(Message message, String response) {
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        reply.setText(response);
        return reply;
    }
}
