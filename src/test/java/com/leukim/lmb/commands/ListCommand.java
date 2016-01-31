package com.leukim.lmb.commands;

import com.leukim.lmb.database.Event;
import org.telegram.telegrambots.api.methods.SendMessage;

import java.util.List;

/**
 * Command that lists all the events.
 *
 * Created by miquel on 30/01/16.
 */
public class ListCommand extends Command {
    @Override
    public SendMessage execute() {
        List<Event> events = database.list();
        if (events.isEmpty()) {
            reply.setText("No events found.");
            return reply;
        }

        StringBuilder replyText = new StringBuilder("Event list:");

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getOwnerID().equals(senderID)) {
                replyText.append("\n\t");
                replyText.append(i);
                replyText.append(") ");
                replyText.append(e.getName());
            }
        }

        reply.setText(replyText.toString());

        return reply;
    }
}
