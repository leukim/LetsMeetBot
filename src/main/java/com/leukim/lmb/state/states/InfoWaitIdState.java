package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.InfoCommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the number of Event to display info about.
 *
 * Created by miquel on 4/2/16.
 */
public class InfoWaitIdState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new InfoCommandExecutor(params);
        Command command = parseMessage(message);
        return executor.execute(command);
    }
}
