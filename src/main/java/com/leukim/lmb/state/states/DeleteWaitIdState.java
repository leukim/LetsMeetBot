package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.DeleteCommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the number of the event to delete.
 *
 * Created by miquel on 04/02/16.
 */
public class DeleteWaitIdState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new DeleteCommandExecutor(params);
        Command command = parseMessage(message);
        return executor.execute(command);
    }
}
