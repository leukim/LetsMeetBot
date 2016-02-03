package com.leukim.lmb.state;

import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Created by miquel on 03/02/16.
 */
public class Result {
    public final State nextState;
    public final SendMessage reply;

    public Result(State nextState, SendMessage reply) {
        this.nextState = nextState;
        this.reply = reply;
    }
}
