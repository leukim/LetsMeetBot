package com.leukim.lmb.commands;

import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Builder for the specific Command subclass depending on
 * the input provided in the make function's message.
 *
 * Created by miquel on 30/01/16.
 */
public enum CommandBuilder {
    UNKNOWN(new UnknownCommand()),
    START(new StartCommand()),
    CREATE(new CreateCommand()),
    DELETE(new DeleteCommand()),
    REMOVE(new DeleteCommand()), // Alias for DELETE
    DELETEALL(new DeleteAllFromCommand()),
    REMOVEALL(new DeleteAllFromCommand()), // Alias for REMOVEALL
    LIST(new ListCommand()),
    INFO(new InfoCommand()),
    CANCEL(new CancelCommand());

    private final Command executor;

    CommandBuilder(Command executorClass) {
        executor = executorClass;
    }

    private void set(List<String> params, Message message, SendMessage reply) {
        executor.set(params, message, reply);
    }

    public static Command make(Message message) {
        if (!message.hasText()) {
            return CommandBuilder.UNKNOWN.executor;
        }

        String messageTxt = message.getText();
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());

        StringTokenizer tokenizer = new StringTokenizer(messageTxt);
        if (!tokenizer.hasMoreTokens()) {
            throw new InvalidParameterException("Could not tokenize input: no parameter found");
        }

        String commandName = tokenizer.nextToken();
        commandName = commandName.replace("/", "").toUpperCase();

        List<String> params = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            params.add(tokenizer.nextToken());
        }

        CommandBuilder commandBuilder;
        try {
            commandBuilder = CommandBuilder.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            commandBuilder = CommandBuilder.UNKNOWN;
        }
        commandBuilder.set(params, message, reply);
        return commandBuilder.executor;
    }
}
