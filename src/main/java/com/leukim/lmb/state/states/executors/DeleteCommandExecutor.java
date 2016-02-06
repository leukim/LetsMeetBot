package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;
import org.apache.commons.lang3.StringUtils;

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
        EventDatabase database = Services.getInstance().getDatabase();
        String eventToDelete = command.message.getText();

        if (StringUtils.equals(eventToDelete, "Cancel")) {
            return new Result(new InitialState(), makeResponse(command.message, "Delete canceled"));
        }

        if (params.containsKey(eventToDelete)) {
            Integer idToDelete = Integer.parseInt(params.get(eventToDelete));
            boolean success = database.delete(idToDelete);
            String replyText;
            if (success) {
                replyText = "Event deleted.";
            } else {
                replyText = "Error deleting event.";
            }
            return new Result(new InitialState(),makeResponse(command.message, replyText));
        } else {
            return new Result(new InitialState(), makeResponse(command.message, "Input was not recognised as event number."));
        }
    }
}
