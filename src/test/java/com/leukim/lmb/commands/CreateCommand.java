package com.leukim.lmb.commands;

import com.leukim.lmb.database.Event;
import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Command used to create a new Event in the DB.
 *
 * Created by miquel on 30/01/16.
 */
public class CreateCommand extends Command {
    @Override
    public SendMessage execute() {
        if (!checkParams()) {
            reply.setText("Wrong usage. Use /create <event name>.");
            return reply;
        }

        String name = params.get(0);

        Event event = Event.create(name, senderID, senderUsername);
        boolean success = database.add(event);
        if (success) {
            reply.setText("Created event " + event.getName());
        } else {
            reply.setText("Could not create event.");
        }

        return reply;
    }

    private boolean checkParams() {
        return params.size() == 1;
    }
}
