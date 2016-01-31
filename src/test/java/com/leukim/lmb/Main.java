package com.leukim.lmb;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.IOException;

/**
 * Main class. Used as entry point.
 *
 * Created by miquel on 30/01/16.
 */
public class Main {

    public static void main(String[] args) {
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            LetsMeetBot letsMeetBot = new LetsMeetBot("src/main/resources/events.txt");
            api.registerBot(letsMeetBot);
        } catch (IOException e) {
            System.out.println("Current dir is " + System.getProperty("user.dir"));
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
