package com.leukim.lmb.state.states.executors;

import com.google.common.collect.Maps;
import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;
import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;
import java.util.Map;

/**
 * Base class for al command executors to extend, providing some utility methods.
 *
 * Created by miquel on 03/02/16.
 */
public abstract class CommandExecutor {
    public abstract Result execute(Command command);

    private SendMessage innerMakeResponse(Message message, String response, boolean enableMarkdown) {
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        reply.setText(response);
        reply.enableMarkdown(enableMarkdown);
        return reply;
    }

    protected SendMessage makeResponse(Message message, String response) {
        return innerMakeResponse(message, response, false);
    }

    protected SendMessage makeResponseWithMarkdown(Message message, String response) {
        return innerMakeResponse(message, response, true);
    }

    protected Result prepareForCollectEvent(Command command, String headerText, Class nextStateClass) {
        EventDatabase database = Services.getInstance().getDatabase();
        List<Event> events = database.list();

        if (events.isEmpty()) {
            SendMessage reply = makeResponse(command.message,"No events found.");
            return new Result(command.state, reply);
        }

        StringBuilder replyText = new StringBuilder(headerText);

        Map<String, String> params = Maps.newHashMap();
        Integer order = 1;
        for (Event e : events) {
            if (e.getOwnerID().equals(command.message.getFrom().getId().toString())) {
                replyText.append("\n\t");
                replyText.append(order);
                replyText.append(") ");
                replyText.append(e.getName());

                params.put(order.toString(),e.getId());
                order++;
            }
        }

        SendMessage reply = makeResponse(command.message, replyText.toString());
        State nextState;
        try {
            nextState = (State) nextStateClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            nextState = new InitialState();
        }
        nextState.setParams(params);
        return new Result(nextState, reply);
    }
}
