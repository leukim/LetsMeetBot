package com.leukim.lmb.database;

/**
 * Class representing an Event in the database.
 *
 * Created by miquel on 30/01/16.
 */
public class Event{
    // Basic event information
    private String id;
    private String name;
    private String conversation;

    // Extra event information
    private String location = "";
    private String time = "";
    private String description = "";

    private Event() {

    }

    public String toString() {
        return "Event *" + name + "* in conversation *" + conversation +
                "*.\n\tLocation: " + location +
                "\n\tTime: " + time +
                "\n\tDescription: " + description;
    }

    public static Event create(String id, String name, String conversation) {
        Event event = new Event();
        event.id = id;
        event.name = name;
        event.conversation = conversation;
        return event;
    }

    public static Event create(String id, String name, String conversation, String location, String time, String description) {
        Event event = create(id, name, conversation);
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

    public String getConversation() {
        return conversation;
    }
}
