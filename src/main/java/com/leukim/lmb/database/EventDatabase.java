package com.leukim.lmb.database;

import java.util.List;
import java.util.Optional;

/**
 * Interface to interact with an Events database.
 *
 * Created by miquel on 01/02/16.
 */
public interface EventDatabase {
    Optional<Event> get(int id);

    boolean add(Event event);

    boolean delete(int id);

    List<Event> list();

    boolean deleteAll();

    boolean deleteAllFrom(String userID);
}