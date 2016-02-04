package com.leukim.lmb.state.states;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.*;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: The initial state. The bot is waiting for a command.
 *
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
            case DELETE:
            case REMOVE:
                executor = new CommandExecutor() {
                    @Override
                    public Result execute(Command command) {
                        return prepareForCollectEvent(command, "Select event to delete:", DeleteWaitIdState.class);
                    }
                };
                break;
            case INFO:
                executor = new CommandExecutor() {
                    @Override
                    public Result execute(Command command) {
                        return prepareForCollectEvent(command, "Select event to get the info:", InfoWaitIdState.class);
                    }
                };
                break;
            case PLAINTEXT:
                executor = new CommandExecutor() {
                    @Override
                    public Result execute(Command command) {
                        return new Result(command.state, makeResponse(command.message, "Please use a command. Try /start for more information."));
                    }
                };
                break;
            default:
                executor = new CommandExecutor() {
                    @Override
                    public Result execute(Command command) {
                        return new Result(command.state, makeResponse(command.message, "Unrecognised command."));
                    }
                };
        }

        return executor.execute(command);
    }

}
