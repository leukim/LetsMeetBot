package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the time of the event to be edited.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitTimeState extends State {
    @Override
    public Result process(Message message) {
        String time = message.getText();
        State nextState = new EditWaitDescriptionState();
        nextState.params = params;
        params.put("time", time);

        return new Result(nextState, makeResponse(message, "Set a description for the event"));
    }
}
