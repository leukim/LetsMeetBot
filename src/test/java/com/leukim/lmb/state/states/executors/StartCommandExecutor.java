package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;

/**
 * Created by miquel on 03/02/16.
 */
public class StartCommandExecutor extends CommandExecutor {
    private static final String START_TEXT = "Available commands:\n" +
            "/start\n" +
            "/create [event name]\n" +
            "/delete [event name] (or /remove [event name]\n" +
            "/deleteall (or /removeall)\n" +
            "/list";

    @Override
    public Result execute(Command command) {
        return new Result(command.state, makeResponse(command.message, START_TEXT));
    }
}
