package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot does not support the conversation in which it is.
 * This state does not transition to any other, bot does not work.
 *
 * Created by miquel on 24/2/16.
 */
public class UnsupportedState extends State {
    @Override
    public Result process(Message message) {
        return new Result(this, makeResponse(message, "Unsupported conversation type."));
    }
}
