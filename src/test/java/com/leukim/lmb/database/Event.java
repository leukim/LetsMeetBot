package com.leukim.lmb.database;

import java.util.StringTokenizer;

/**
 * Class representing an Event in the database.
 *
 * Created by miquel on 30/01/16.
 */
public class Event{
    private String id;
    private String name;
    private String ownerID;
    private String ownerUsername;

    private Event() {

    }

    public String toString() {
        return name + " " + ownerID + " " + ownerUsername;
    }

    public static Event fromString(String eventString) {
        StringTokenizer tokenizer = new StringTokenizer(eventString);

        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        String eventName = tokenizer.nextToken();

        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        String eventOwnerID = tokenizer.nextToken();

        if (!tokenizer.hasMoreTokens()) {
            return null;
        }

        String eventOwnerUsername = tokenizer.nextToken();

        Event event = new Event();
        event.name = eventName;
        event.ownerID = eventOwnerID;
        event.ownerUsername = eventOwnerUsername;
        return event;
    }

    public static Event create(String id, String name, String ownerID, String ownerUsername) {
        Event event = new Event();
        event.id = id;
        event.name = name;
        event.ownerID = ownerID;
        event.ownerUsername = ownerUsername;
        return event;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    private boolean hasOwnerUsername() {
        return ownerUsername != null && !ownerUsername.isEmpty();
    }

    public String getOwnerUsernameOrID() {
        return hasOwnerUsername() ? ownerUsername : ownerID;
    }
}
