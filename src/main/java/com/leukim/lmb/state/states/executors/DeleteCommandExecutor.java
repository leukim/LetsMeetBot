package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;

import java.util.Map;

/**
 * Command executor that deletes an Event.
 *
 * Created by miquel on 04/02/16.
 */
public class DeleteCommandExecutor extends CommandExecutor {

    private final Map<String,String> params;

    public DeleteCommandExecutor(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Result execute(Command command) {
        Event event;
        try {
            event = collectSelectedEvent(command,params);
        } catch (CancelWorkflowException e) {
            return resetState(command.message, "Command cancelled");
        } catch (EventNotFoundException e) {
            return resetState(command.message, "Event not found.");
        }

        EventDatabase database = Services.getInstance().getDatabase();
        Integer idToDelete = Integer.parseInt(event.getId());
        String replyText;
        if (database.delete(idToDelete)) {
            replyText = "Event deleted.";
        } else {
            replyText = "Error deleting event.";
        }
        return new Result(new InitialState(),makeResponse(command.message, replyText));
    }
}
