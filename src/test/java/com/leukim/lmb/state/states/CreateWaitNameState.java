package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.CreateEventExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by miquel on 03/02/16.
 */
public class CreateWaitNameState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new CreateEventExecutor();
        return executor.execute(parseMessage(message));
    }
}
