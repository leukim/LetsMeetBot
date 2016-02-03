package com.leukim.lmb.state.states.executors;

import com.google.common.collect.Maps;
import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.DeleteWaitIdState;
import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.methods.SendMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by miquel on 04/02/16.
 */
public class DeleteListExecutor extends CommandExecutor {
    @Override
    public Result execute(Command command) {
        EventDatabase database = Services.getInstance().getDatabase();
        List<Event> events = database.list();

        if (events.isEmpty()) {
            SendMessage reply = makeResponse(command.message,"No events found.");
            return new Result(command.state, reply);
        }

        StringBuilder replyText = new StringBuilder("Select event to delete:");

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
        State nextState = new DeleteWaitIdState();
        nextState.setParams(params);
        return new Result(nextState, reply);
    }
}
