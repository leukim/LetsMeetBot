package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import com.leukim.lmb.state.states.executors.ListCommandExecutor;
import com.leukim.lmb.state.states.executors.StartCommandExecutor;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by miquel on 03/02/16.
 */
public class InitialState extends State {
    @Override
    public Result process(Message message) {
        Command command = parseMessage(message);

        CommandExecutor executor;

        switch (command.type) {
            case START:
                executor = new StartCommandExecutor();
                break;
            case LIST:
                executor = new ListCommandExecutor();
                break;
            case CREATE:
                executor = new CommandExecutor() {
                    @Override
                    public Result execute(Command command) {
                        SendMessage reply = makeResponse(command.message, "What should we call this Event?");
                        State nextState = new CreateWaitNameState();
                        return new Result(nextState, reply);
                    }
                };
                break;
            default:
                // TODO Remove this when all commands are reimplemented
                return null;
        }

        return executor.execute(command);
    }

}
