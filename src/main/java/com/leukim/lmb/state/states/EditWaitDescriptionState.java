package com.leukim.lmb.state.states;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Result;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the input of the event description.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitDescriptionState extends State {
    @Override
    public Result process(Message message) {
        String description = message.getText();

        EventDatabase database = Services.getInstance().getDatabase();
        Integer id = Integer.parseInt(params.get("id"));
        String location = params.get("location");
        String time = params.get("time");

        boolean success = database.addInformation(id, location, time, description);
        String replyText;
        if (success) {
            replyText = "Event updated.";
        } else {
            replyText = "Could not update event.";
        }
        return new Result(new InitialState(), makeResponse(message, replyText));
    }
}
