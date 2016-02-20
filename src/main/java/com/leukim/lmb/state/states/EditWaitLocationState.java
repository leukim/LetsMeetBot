package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the location of the event to be edited.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitLocationState extends State {
    @Override
    public Result process(Message message) {
        String location = message.getText();
        params.put("location", location);
        State nextState = new EditWaitTimeState();
        nextState.params = params;

        return new Result(nextState, makeResponse(message, "Set a time/date for the event (dd/mm/yy [hh:mm])"));
    }
}
