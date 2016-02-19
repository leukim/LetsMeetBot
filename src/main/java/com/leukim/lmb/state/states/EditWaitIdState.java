package com.leukim.lmb.state.states;

import com.leukim.lmb.database.Event;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the selection of the event to edit.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitIdState extends State {
    @Override
    public Result process(Message message) {
        CommandExecutor executor = new CommandExecutor() {
            @Override
            public Result execute(Command command) {
                State nextState = new EditWaitLocationState();
                nextState.params = params;

                Event event;

                try {
                    event = collectSelectedEvent(command, params);
                } catch (CancelWorkflowException e) {
                    return resetState(message, "Command cancelled.");
                } catch (EventNotFoundException e) {
                    return resetState(message, "Event not found.");
                }

                nextState.params.put("id", event.getId());

                return new Result(nextState, makeResponse(message, "Set a location for the event:"));
            }
        };

        return executor.execute(parseMessage(message));
    }
}
