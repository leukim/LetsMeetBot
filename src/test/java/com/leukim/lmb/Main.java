package com.leukim.lmb;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * Main class. Used as entry point.
 *
 * Created by miquel on 30/01/16.
 */
public class Main {

    public static void main(String[] args) {
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            LetsMeetBot letsMeetBot = new LetsMeetBot();
            api.registerBot(letsMeetBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
