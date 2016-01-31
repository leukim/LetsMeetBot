package com.leukim.lmb.commands;

import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Command used to reply when no known command was found.
 *
 * Created by miquel on 30/01/16.
 */
public class UnknownCommand extends Command {
    @Override
    public SendMessage execute() {
        reply.setText("Unknown command. Try /start for help.");
        return reply;
    }
}
