package com.leukim.lmb;

import com.leukim.lmb.database.EventDatabase;
import com.leukim.lmb.database.SQLiteDatabase;

/**
 * Singleton object containing service classes.
 *
 * Created by miquel on 02/02/16.
 */
public class Services {

    private static Services instance;

    private EventDatabase database;

    private Services() {
        try {
            database = new SQLiteDatabase();
        } catch (LMBException e) {
            e.printStackTrace();
        }
    }

    public static Services getInstance() {
        if (instance == null) {
            instance = new Services();
        }

        return instance;
    }

    public EventDatabase getDatabase() {
        return database;
    }
}
