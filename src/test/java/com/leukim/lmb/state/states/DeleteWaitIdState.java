package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.DeleteEventExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by miquel on 04/02/16.
 */
public class DeleteWaitIdState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new DeleteEventExecutor(params);
        Command command = parseMessage(message);
        return executor.execute(command);
    }
}
