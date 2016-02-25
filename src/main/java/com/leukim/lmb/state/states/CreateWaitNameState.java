package com.leukim.lmb.state.states;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for a name for the event to be created.
 *
 * Created by miquel on 03/02/16.
 */
public class CreateWaitNameState extends State {
    @Override
    public Result process(Message message) {
        Event event = Event.create(null,message.getText(), message.getChat().getId().toString());
        EventDatabase database = Services.getInstance().getDatabase();
        boolean success = database.add(event);
        SendMessage reply;
        if (success) {
            reply = makeResponse(message, "Created event " + event.getName());
        } else {
            reply = makeResponse(message, "Could not create event.");
        }

        return new Result(new InitialState(), reply);
    }
}
