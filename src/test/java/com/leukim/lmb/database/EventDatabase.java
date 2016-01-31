package com.leukim.lmb.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to interact with the underlying database of Events.
 *
 * Created by miquel on 30/01/16.
 */
public class EventDatabase {

    private final Path filePath;
    private List<Event> events;

    public EventDatabase(String filePath) throws IOException {
        this.filePath = Paths.get(filePath);
        parseFile();
    }

    private void parseFile() throws IOException {
        Stream<String> lines = Files.lines(filePath);
        events = lines.map(Event::fromString)
                .filter(event -> !event.getName().isEmpty())
                .collect(Collectors.toList());
    }

    private void writeFile() throws IOException {
        List<String> se = events.stream().map(Event::toString).collect(Collectors.toList());
        Files.write(filePath, se);
    }

    public Event get(int id) {
        if (events.size() <= id) {
            throw new NoSuchElementException("Event does not exist in DB.");
        }
        return events.get(id);
    }

    public Optional<Event> get(String name) {
        return events.stream().filter(event -> event.getName().equals(name)).findFirst();
    }

    public boolean add(Event event) {
        events.add(event);
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(int id) {
        if (events.size() <= id) {
            return false;
        }

        events.remove(id);

        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(String name) {
        if (events.stream().filter(event -> event.getName().equals(name)).count() == 0) {
            return false;
        }

        events = events.stream().filter(event -> !event.getName().equals(name)).collect(Collectors.toList());

        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Event> list() {
        return events;
    }

    public boolean deleteAll() {
        List<Event> eventsCopy = new ArrayList<>(events);
        events = new ArrayList<>();
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            events = eventsCopy;
            return false;
        }

        return true;
    }

    public boolean deleteAllFrom(String userID) {
        List<Event> eventsCopy = new ArrayList<>(events);
        events = events.stream().filter(event -> !event.getOwnerID().equals(userID)).collect(Collectors.toList());
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            events = eventsCopy;
            return false;
        }
        return true;
    }
}
