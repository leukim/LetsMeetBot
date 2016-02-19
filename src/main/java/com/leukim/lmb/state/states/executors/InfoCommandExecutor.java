package com.leukim.lmb.state.states.executors;

import com.leukim.lmb.database.Event;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.InitialState;

import java.util.Map;

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
        try {

            Event event = collectSelectedEvent(command, params);
            String replyText = event.toString();//"Event *" + event.getName() + "* created by *" + event.getOwnerUsernameOrID() + "*.";

            return new Result(new InitialState(), makeResponseWithMarkdown(command.message, replyText));

        } catch (CancelWorkflowException e) {
            return new Result(new InitialState(), makeResponse(command.message, "Command canceled"));
        } catch (EventNotFoundException e) {
            return new Result(new InitialState(), makeResponse(command.message, "Input was not recognised as event number."));
        }
    }
}
