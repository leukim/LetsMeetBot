package com.leukim.lmb.state.states;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

/**
 * State: The initial state. The bot is waiting for a command.
 *
 * Created by miquel on 03/02/16.
 */
public class InitialState extends State {
    @Override
    public Result process(Message message) {
        Command command = parseMessage(message);
        SendMessage reply;

        switch (command.type) {
            case START:
                String START_TEXT = "Available commands:\n" +
                        "/start\n" +
                        "/create\n" +
                        "/delete (or /remove)\n" +
                        "/list";
                return new Result(command.state, makeResponse(command.message, START_TEXT));

            case LIST:
                EventDatabase database = Services.getInstance().getDatabase();
                List<Event> events = database.list();

                if (events.isEmpty()) {
                    reply = makeResponse(command.message,"No events found.");
                    return new Result(command.state, reply);
                }

                StringBuilder replyText = new StringBuilder("Event list:");

                int index = 1;
                for (Event e : events) {
                    if (e.getConversation().equals(command.message.getChat().getId().toString())) {
                        replyText.append("\n\t");
                        replyText.append(index);
                        replyText.append(") ");
                        replyText.append(e.getName());
                        index++;
                    }
                }

                reply = makeResponse(command.message, replyText.toString());
                return new Result(command.state, reply);

            case CREATE:
                String replyToUsername = message.getFrom().getUserName();
                if (replyToUsername == null) {
                    return errorNoUsername(message);
                }
                reply = makeResponse(message, "@" + replyToUsername + ", what should we call this Event?");
                ForceReplyKeyboard kbd = new ForceReplyKeyboard();
                kbd.setSelective(true);
                reply.setReplayMarkup(kbd);
                State nextState = new CreateWaitNameState();
                return new Result(nextState, reply);

            case DELETE:
            case REMOVE:
                return prepareForCollectEvent(message, "Select event to delete:", DeleteWaitIdState.class);

            case INFO:
                return prepareForCollectEvent(message, "Select event to get the info:", InfoWaitIdState.class);

            case EDIT:
                return prepareForCollectEvent(message, "Select event to edit:", EditWaitIdState.class);
            case PLAINTEXT:
                return new Result(command.state, makeResponse(command.message, "Please use a command. Try /start for more information."));
        }

        return new Result(command.state, makeResponse(command.message, "Unrecognised command."));
    }

}
