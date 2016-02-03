package com.leukim.lmb.state;

import com.leukim.lmb.state.states.InitialState;
import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by miquel on 02/02/16.
 */
public class Conversation {

    private State state;

    public Conversation() {
        state = new InitialState();
    }

    public SendMessage process(Message message) {
        Result result = state.process(message);
        state = result.nextState;
        return result.reply;
    }
}
