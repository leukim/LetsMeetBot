package com.leukim.lmb.state;

import java.util.HashMap;

/**
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
