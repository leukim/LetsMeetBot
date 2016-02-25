package com.leukim.lmb.state;

import com.leukim.lmb.state.states.InitialState;
import com.leukim.lmb.state.states.State;
import com.leukim.lmb.state.states.UnsupportedState;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Object that holds and manages the state of a single conversation with the bot.
 *
 * Created by miquel on 02/02/16.
 */
public class Conversation {

    private State state;

    public Conversation(Chat chat) {
        if (chat.isUserChat()) {
            state = new UnsupportedState();
        } else if (chat.isGroupChat() || chat.isGroupChat()) {
            state = new InitialState();
        } else {
            state = new UnsupportedState();
        }
    }

    public SendMessage process(Message message) {
        Result result = state.process(message);
        state = result.nextState;
        return result.reply;
    }
}
