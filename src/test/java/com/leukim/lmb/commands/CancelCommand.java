package com.leukim.lmb.commands;

import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Null command, results in no operation.
 * Used when a multistep command is cancelled
 *
 * Created by miquel on 31/01/16.
 */
public class CancelCommand extends Command {
    @Override
    public SendMessage execute() {
        return null;
    }
}
