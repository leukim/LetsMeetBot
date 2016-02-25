package com.leukim.lmb.state;

import org.telegram.telegrambots.api.objects.Chat;

import java.util.HashMap;

/**
 * Pretty-print for the Map containing all the conversation IDs mapping to their corresponding conversation object.
 *
 * Created by miquel on 02/02/16.
 */
public class BotStatus extends HashMap<Long, Conversation> {

    public Conversation newConversation(Chat chat) {
        Conversation conversation = new Conversation(chat);
        put(chat.getId(), conversation);
        return conversation;
    }

    public Conversation getConversationOrNew(Chat chat) {
        if (containsKey(chat.getId())) {
            return get(chat.getId());
        }

        return newConversation(chat);
    }

}
