package com.leukim.lmb.state.states;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the number of the event to delete.
 *
 * Created by miquel on 04/02/16.
 */
public class DeleteWaitIdState extends State {
    @Override
    public Result process(Message message) {
        Event event;
        try {
            event = collectSelectedEvent(message, params);
        } catch (CancelWorkflowException e) {
            return resetState(message, "Command cancelled");
        } catch (EventNotFoundException e) {
            return resetState(message, "Event not found.");
        }

        EventDatabase database = Services.getInstance().getDatabase();
        Integer idToDelete = Integer.parseInt(event.getId());
        String replyText;
        if (database.delete(idToDelete)) {
            replyText = "Event deleted.";
        } else {
            replyText = "Error deleting event.";
        }
        return new Result(new InitialState(),makeResponse(message, replyText));
    }
}
