package com.leukim.lmb.state;

import java.util.HashMap;

/**
 * Pretty-print for the Map containing all the conversation IDs mapping to their corresponding conversation object.
 *
 * Created by miquel on 02/02/16.
 */
public class BotStatus extends HashMap<Long, Conversation> {

    public Conversation newConversation(Long chatID) {
        Conversation conversation = new Conversation();
        put(chatID, conversation);
        return conversation;
    }

    public Conversation getConversationOrNew(Long chatID) {
        if (containsKey(chatID)) {
            return get(chatID);
        }

        return newConversation(chatID);
    }

}
