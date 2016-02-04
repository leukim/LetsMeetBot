package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;
import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Command executor that creates a new Event.
 *
 * Created by miquel on 03/02/16.
 */
public class CreateCommandExecutor extends CommandExecutor {

    @Override
    public Result execute(Command command) {
        Event event = Event.create(null,command.message.getText(), command.message.getFrom().getId().toString(), command.message.getFrom().getUserName());
        EventDatabase database = Services.getInstance().getDatabase();
        boolean success = database.add(event);
        SendMessage reply;
        if (success) {
            reply = makeResponse(command.message, "Created event " + event.getName());
        } else {
            reply = makeResponse(command.message, "Could not create event.");
        }

        return new Result(new InitialState(), reply);
    }
}