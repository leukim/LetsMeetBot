package com.leukim.lmb.database;

import java.util.StringTokenizer;

/**
 * Class representing an Event in the database.
 *
 * Created by miquel on 30/01/16.
 */
public class Event{
    // Basic event information
    private String id;
    private String name;
    private String ownerID;
    private String ownerUsername;

    // Extra event information
    private String location = "";
    private String time = "";
    private String description = "";

    private Event() {

    }

    public String toString() {
        return "Event *" + name + "* created by *" + getOwnerUsernameOrID() +
                "*.\n\tLocation: " + location +
                "\n\tTime: " + time +
                "\n\tDescription: " + description;
    }

    public static Event create(String id, String name, String ownerID, String ownerUsername) {
        Event event = new Event();
        event.id = id;
        event.name = name;
        event.ownerID = ownerID;
        event.ownerUsername = ownerUsername;
        return event;
    }

    public static Event create(String id, String name, String ownerID, String ownerUsername, String location, String time, String description) {
        Event event = create(id, name, ownerID, ownerUsername);
        event.location = location;
        event.time = time;
        event.description = description;
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
