package com.leukim.lmb.commands;

import com.leukim.lmb.database.Event;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command to delete a specific event.
 *
 * Created by miquel on 30/01/16.
 */
public class DeleteCommand extends Command {
    @Override
    public SendMessage execute() {
        if (!checkParams()) {
            return sendMessage("Wrong usage. Use /delete <event name>");
        }

        if (!hasIDParam()) {
            reply.setReplayMarkup(createKeyboard());
            reply.setText("Select event to delete.");
            return reply;
        }

        String eventName = params.get(0);

        Optional<Event> eventOpt = database.get(eventName);
        if (!eventOpt.isPresent()) {
            return sendMessage("Event does not exist.");
        }

        Event event = eventOpt.get();

        if (!event.getOwnerID().equals(senderID)) {
            return sendMessage("You cannot delete this event.");
        }

        boolean success = database.delete(eventName);
        if (success) {
            reply.setText("Removed event " + eventName);
        } else {
            reply.setText("Could not remove event " + eventName);
        }

        return reply;
    }

    private boolean checkParams() {
        return params.size() <= 1;
    }

    private boolean hasIDParam() {
        return params.size() == 1;
    }

    private ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setOneTimeKeyboad(true);

        List<String> entries = database.list().stream()
                .filter(event -> event.getOwnerID().equals(senderID))
                .map(Event::getName)
                .map(entry -> "/delete " + entry)
                .collect(Collectors.toList());

        entries.add("/cancel");
        List<List<String>> keyboardEntries = new ArrayList<>();
        keyboardEntries.add(entries);
        replyKeyboard.setKeyboard(keyboardEntries);
        return replyKeyboard;
    }
}
