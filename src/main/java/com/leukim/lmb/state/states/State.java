package com.leukim.lmb.state.states;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Base class for the different bot states to extend from, and which provides some utility methods.
 *
 * Created by miquel on 03/02/16.
 */
public abstract class State {
    Map<String, String> params;

    public abstract Result process(Message message);

    Command parseMessage(Message message) {
        if (message.hasText()) {
            String messageText = message.getText();
            if (isCommand(messageText)) {
                StringTokenizer tokenizer = new StringTokenizer(messageText);

                if (!tokenizer.hasMoreTokens()) {
                    throw new IllegalStateException();
                }

                String name = tokenizer.nextToken();
                name = StringUtils.replace(name, "/", "");
                name = name.toUpperCase();

                List<String> params = Lists.newArrayList();

                while (tokenizer.hasMoreTokens()) {
                    params.add(tokenizer.nextToken());
                }

                Command.Type type;
                try {
                    type = Command.Type.valueOf(name);
                } catch (IllegalArgumentException e) {
                    type = Command.Type.UNKNOWN;
                }

                return new Command(this, message, type, params);
            } else {
                return new Command(this, message, Command.Type.PLAINTEXT, Lists.newArrayList());
            }
        }
        return new Command(this, message, Command.Type.UNKNOWN, Lists.newArrayList());
    }

    private boolean isCommand(String message) {
        return StringUtils.startsWith(message, "/");
    }

    private SendMessage innerMakeResponse(Message message, String response, boolean enableMarkdown) {
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        reply.setText(response);
        reply.enableMarkdown(enableMarkdown);
        return reply;
    }

    SendMessage makeResponse(Message message, String response) {
        return innerMakeResponse(message, response, false);
    }

    SendMessage makeResponseWithMarkdown(Message message, String response) {
        return innerMakeResponse(message, response, true);
    }

    Result prepareForCollectEvent(Command command, String headerText, Class nextStateClass) {
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
        reply.setReplayMarkup(getCustomKeyBoard(params));

        State nextState;
        try {
            nextState = (State) nextStateClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            nextState = new InitialState();
        }
        nextState.params = params;
        return new Result(nextState, reply);
    }

    private ReplyKeyboardMarkup getCustomKeyBoard(Map<String, String> params) {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setOneTimeKeyboad(true);

        List<List<String>> keyboardEntries = params.keySet().stream().map(Lists::newArrayList).collect(Collectors.toList());
        keyboardEntries.add(Lists.newArrayList("Cancel"));
        replyKeyboard.setKeyboard(keyboardEntries);

        return replyKeyboard;
    }

    Result resetState(Message message, String replyText) {
        return new Result(new InitialState(), makeResponse(message, replyText));
    }

    Event collectSelectedEvent(Message message, Map<String, String> params) throws CancelWorkflowException, EventNotFoundException {
        EventDatabase database = Services.getInstance().getDatabase();
        String eventToRetrieve = message.getText();

        if (StringUtils.equals(eventToRetrieve, "Cancel")) {
            throw new CancelWorkflowException();
        }

        if (!params.containsKey(eventToRetrieve)) {
            throw new EventNotFoundException();
        }

        Integer idToRetrieve = Integer.parseInt(params.get(eventToRetrieve));

        Optional<Event> eventOptional = database.get(idToRetrieve);

        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException();
        }

        return eventOptional.get();
    }

    class CancelWorkflowException extends Exception {}
    class EventNotFoundException extends Exception {}
}
