package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.Event;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * Command executor that retrieves and displays the information of a single Event.
 *
 * Created by miquel on 4/2/16.
 */
public class InfoCommandExecutor extends CommandExecutor {

    private final Map<String,String> params;

    public InfoCommandExecutor(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Result execute(Command command) {
        EventDatabase database = Services.getInstance().getDatabase();
        String eventToRetrieve = command.message.getText();

        if (StringUtils.equals(eventToRetrieve, "Cancel")) {
            return new Result(new InitialState(), makeResponse(command.message, "Command canceled"));
        }

        if (params.containsKey(eventToRetrieve)) {
            Integer idToRetrieve = Integer.parseInt(params.get(eventToRetrieve));
            Optional<Event> eventOptional = database.get(idToRetrieve);
            if (!eventOptional.isPresent()) {
                return new Result(new InitialState(), makeResponse(command.message, "Could not retrieve selected event."));
            }
            Event event = eventOptional.get();
            String replyText = "Event *" + event.getName() + "* created by *" + event.getOwnerUsernameOrID() + "*.";

            return new Result(new InitialState(), makeResponseWithMarkdown(command.message, replyText));
        } else {
            return new Result(new InitialState(), makeResponse(command.message, "Input was not recognised as event number."));
        }
    }
}
