package com.leukim.lmb.commands;

import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Command to display information about the bot's capabilities.
 *
 * Created by miquel on 30/01/16.
 */
public class StartCommand extends Command {

    @Override
    public SendMessage execute() {
        String START_TEXT = "Available commands:\n" +
                "/start\n" +
                "/create <event name>\n" +
                "/delete [event name] (or /remove [event name]\n" +
                "/deleteall (or /removeall)\n" +
                "/list";
        reply.setText(START_TEXT);
        reply.enableMarkdown(true);

        return reply;
    }
}
