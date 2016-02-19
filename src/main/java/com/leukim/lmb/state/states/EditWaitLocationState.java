package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the location of the event to be edited.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitLocationState extends State {
    @Override
    public Result process(Message message) {
        return new CommandExecutor() {
            @Override
            public Result execute(Command command) {

                String location = command.message.getText();
                State nextState = new EditWaitTimeState();
                nextState.params = params;
                params.put("location", location);

                return new Result(nextState, makeResponse(message, "Set a time/date for the event (dd/mm/yy [hh:mm])"));
            }
        }.execute(parseMessage(message));
    }
}
