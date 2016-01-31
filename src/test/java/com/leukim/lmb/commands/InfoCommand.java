package com.leukim.lmb.commands;

import com.leukim.lmb.database.Event;
import jersey.repackaged.com.google.common.collect.Lists;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Command to retrieve the information of a specific Event.
 *
 * Created by miquel on 31/01/16.
 */
public class InfoCommand extends Command {
    @Override
    public SendMessage execute() {
        if (hasParams(0)) {
            return sendChoice();
        } else if (hasParams(1)) {
            return sendInfo();
        }

        return sendMessage("Wrong usage. Use /info [event name]");
    }

    private SendMessage sendInfo() {
        String name = params.get(0);
        Optional<Event> eventOptional = database.get(name);

        if (!eventOptional.isPresent()) {
            return sendMessage("The event does not exist.");
        }

        Event event = eventOptional.get();
        String eventName = event.getName();
        String eventUser = event.getOwnerUsernameOrID();

        reply.enableMarkdown(true);
        return sendMessage("Event *" + eventName + "* created by *" + eventUser + "*.");
    }

    private SendMessage sendChoice() {
        reply.setReplayMarkup(createKeyboard());
        return sendMessage("Choose event to display.");
    }

    private ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setOneTimeKeyboad(true);

        List<String> entries = database.list().stream()
                .filter(event -> event.getOwnerID().equals(senderID))
                .map(Event::getName)
                .map(entry -> "/info " + entry)
                .collect(Collectors.toList());

        entries.add("/cancel");
        List<List<String>> keyboardEntries = new ArrayList<>();
        for (String s : entries) {
            keyboardEntries.add(Lists.newArrayList(s));
        }
        replyKeyboard.setKeyboard(keyboardEntries);
        return replyKeyboard;
    }
}
