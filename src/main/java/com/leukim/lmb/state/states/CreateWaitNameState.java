package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.CreateCommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for a name for the event to be created.
 *
 * Created by miquel on 03/02/16.
 */
public class CreateWaitNameState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new CreateCommandExecutor();
        return executor.execute(parseMessage(message));
    }
}
