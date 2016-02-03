package com.leukim.lmb.state.states;

import com.google.common.collect.Lists;
import com.leukim.lmb.state.Command;
import com.leukim.lmb.state.Result;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by miquel on 03/02/16.
 */
public abstract class State {
    Map<String, String> params;

    public abstract Result process(Message message);

    Command parseMessage(Message message) {
        if (message.hasText()) {
            String messageText = message.getText();
            if (isCommand(messageText)) {
                StringTokenizer tokenizer = new StringTokenizer(messageText);

                if (!tokenizer.hasMoreTokens()) {
                    throw new IllegalStateException();
                }

                String name = tokenizer.nextToken();
                name = StringUtils.replace(name, "/", "");
                name = name.toUpperCase();

                List<String> params = Lists.newArrayList();

                while (tokenizer.hasMoreTokens()) {
                    params.add(tokenizer.nextToken());
                }

                return new Command(this, message, Command.Type.valueOf(name), params);
            } else {
                return new Command(this, message, Command.Type.PLAINTEXT, Lists.newArrayList());
            }
        }
        return new Command(this, message, Command.Type.UNKNOWN, Lists.newArrayList());
    }

    private boolean isCommand(String message) {
        return StringUtils.startsWith(message, "/");
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
