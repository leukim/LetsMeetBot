package com.leukim.lmb.state;

import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

/**
 * Created by miquel on 03/02/16.
 */
public class Command {
    public final State state;
    public final Message message;
    public final Type type;
    public final List<String> params;

    public Command(State state, Message message, Type type, List<String> params) {
        this.state = state;
        this.message = message;
        this.type = type;
        this.params = params;
    }

    public enum Type {
        UNKNOWN, PLAINTEXT, START, LIST, CREATE
    }
}
