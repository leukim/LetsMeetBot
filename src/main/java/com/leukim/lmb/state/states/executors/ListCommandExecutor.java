package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.methods.SendMessage;

import java.util.List;

/**
 * Command executor that retrieves the list of events.
 *
 * Created by miquel on 03/02/16.
 */
public class ListCommandExecutor extends CommandExecutor {
    @Override
    public Result execute(Command command) {
        EventDatabase database = Services.getInstance().getDatabase();
        List<Event> events = database.list();

        if (events.isEmpty()) {
            SendMessage reply = makeResponse(command.message,"No events found.");
            return new Result(command.state, reply);
        }

        StringBuilder replyText = new StringBuilder("Event list:");

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getOwnerID().equals(command.message.getFrom().getId().toString())) {
                replyText.append("\n\t");
                replyText.append(i);
                replyText.append(") ");
                replyText.append(e.getName());
            }
        }

        SendMessage reply = makeResponse(command.message, replyText.toString());
        return new Result(command.state, reply);
    }
}
