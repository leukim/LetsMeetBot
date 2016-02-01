package com.leukim.lmb.database;

import jersey.repackaged.com.google.common.collect.Lists;
import org.telegram.telegrambots.TelegramApiException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Class to interact with a SQLite-based Event database.
 *
 * Created by miquel on 01/02/16.
 */
public class SQLiteDatabase implements EventDatabase {

    private Connection connection;

    public SQLiteDatabase() throws TelegramApiException {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = null;

            connection = DriverManager.getConnection("jdbc:sqlite:events.sqlite");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Events (id INTEGER PRIMARY KEY, name TEXT, ownerID TEXT, ownerUsername TEXT)");
        } catch (ClassNotFoundException e) {
            throw new TelegramApiException("Could not load the database driver", e);
        } catch (SQLException e) {
            throw new TelegramApiException("There was an exception in the SQL driver", e);
        }
    }

    @Override
    public Optional<Event> get(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE id='"+id+"'");

            if (rs.next()) {
                Event event = make(rs);
                return Optional.of(event);
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> get(String name) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events WHERE name='"+name+"'");

            if (rs.next()) {
                Event event = make(rs);
                return Optional.of(event);
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean add(Event event) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Events VALUES (NULL,'"+event.getName()+"','"+event.getOwnerID()+"','"+event.getOwnerUsername()+"')");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events WHERE id='"+id+"'");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String name) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events WHERE name='"+name+"'");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Event> list() {
        List<Event> results = Lists.newArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Events");

            while (rs.next()) {
                Event event = make(rs);
                results.add(event);
            }

        } catch (SQLException e) {
            return Lists.newArrayList();
        }
        return results;
    }

    @Override
    public boolean deleteAll() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAllFrom(String userID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Events WHERE ownerID='"+userID+"'");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private Event make(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String ownerId = rs.getString("ownerID");
        String ownerUsername = rs.getString("ownerUsername");

        return Event.create(name, ownerId, ownerUsername);
    }
}
