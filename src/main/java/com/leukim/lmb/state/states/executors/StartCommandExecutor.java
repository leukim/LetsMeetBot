package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;

/**
 * Command executor that displays how to use the bot.
 *
 * Created by miquel on 03/02/16.
 */
public class StartCommandExecutor extends CommandExecutor {
    private static final String START_TEXT = "Available commands:\n" +
            "/start\n" +
            "/create\n" +
            "/delete (or /remove)\n" +
            "/list";

    @Override
    public Result execute(Command command) {
        return new Result(command.state, makeResponse(command.message, START_TEXT));
    }
}
