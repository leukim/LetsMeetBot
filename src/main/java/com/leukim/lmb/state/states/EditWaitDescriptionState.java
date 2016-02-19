package com.leukim.lmb.state.states;

import com.leukim.lmb.Services;
import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import com.leukim.lmb.state.states.executors.CommandExecutor;
import org.telegram.telegrambots.api.objects.Message;

/**
 * State: the bot is waiting for the input of the event description.
 *
 * Created by miquel on 19/2/16.
 */
public class EditWaitDescriptionState extends State {
    @Override
    public Result process(Message message) {
        Command command = parseMessage(message);

        String description = message.getText();
        params.put("description", description);

        return new CommandExecutor() {

            @Override
            public Result execute(Command command) {
                EventDatabase database = Services.getInstance().getDatabase();
                Integer id = Integer.parseInt(params.get("id"));
                String location = params.get("location");
                String time = params.get("time");
                String description = params.get("description");

                boolean success = database.addInformation(id, location, time, description);
                String replyText;
                if (success) {
                    replyText = "Event updated.";
                } else {
                    replyText = "Could not update event.";
                }
                return new Result(new InitialState(), makeResponse(message, replyText));
            }
        }.execute(command);
    }
}
