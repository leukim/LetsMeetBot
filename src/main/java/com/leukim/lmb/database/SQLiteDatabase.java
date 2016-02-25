package com.leukim.lmb.database;

import com.google.common.collect.Lists;
import com.leukim.lmb.LMBException;
import org.apache.commons.lang3.StringUtils;

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

    public SQLiteDatabase() throws LMBException {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = null;

            connection = DriverManager.getConnection("jdbc:sqlite:events.sqlite");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Events (id INTEGER PRIMARY KEY, name TEXT, conversation TEXT, location TEXT, time TEXT, description TEXT)");
        } catch (ClassNotFoundException e) {
            throw new LMBException("Could not load the database driver", e);
        } catch (SQLException e) {
            throw new LMBException("There was an exception in the SQL driver", e);
        }
    }

    @Override
    public Optional<Event> get(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Events WHERE id=?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Events (name, conversation) VALUES (?,?)");
            statement.setString(1, event.getName());
            statement.setString(2, event.getConversation());
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Events WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
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
    public boolean addInformation(Integer id, String location, String time, String description) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Events SET location=?, time=?, description=? WHERE id=?");
            statement.setString(1, location);
            statement.setString(2, time);
            statement.setString(3, description);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private Event make(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String conversation = rs.getString("conversation");

        String location = rs.getString("location");
        String time = rs.getString("time");
        String description = rs.getString("description");

        if (StringUtils.isNotEmpty(location) || StringUtils.isNotEmpty(time) || StringUtils.isNotEmpty(description)) {
            return Event.create(id, name, conversation, location, time, description);
        } else {
            return Event.create(id, name, conversation);
        }
    }
}
