package com.leukim.lmb.commands;

import org.telegram.telegrambots.api.methods.SendMessage;

/**
 * Command to delete all the Events from the current user.
 *
 * Created by miquel on 30/01/16.
 */
public class DeleteAllFromCommand extends Command {
    @Override
    public SendMessage execute() {
        boolean success = database.deleteAllFrom(senderID);
        if (success) {
            reply.setText("Deleted all your events.");
        } else {
            reply.setText("Could not delete all your events.");
        }

        return reply;
    }
}
