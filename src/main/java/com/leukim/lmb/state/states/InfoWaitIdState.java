package com.leukim.lmb.state.states;

import com.leukim.lmb.database.Event;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the number of Event to display info about.
 *
 * Created by miquel on 4/2/16.
 */
public class InfoWaitIdState extends State {
    @Override
    public Result process(Message message) {
        try {

            Event event = collectSelectedEvent(message, params);
            String replyText = event.toString();

            return new Result(new InitialState(), makeResponseWithMarkdown(message, replyText));

        } catch (CancelWorkflowException e) {
            return new Result(new InitialState(), makeResponse(message, "Command canceled"));
        } catch (EventNotFoundException e) {
            return new Result(new InitialState(), makeResponse(message, "Input was not recognised as event number."));
        }
    }
}
