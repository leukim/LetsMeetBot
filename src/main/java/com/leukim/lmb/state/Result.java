package com.leukim.lmb.state;

import com.leukim.lmb.state.states.State;
import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Data object used to return both the next state of the bot and the message to be sent as reply.
 *
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
