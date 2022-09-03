package de.bytestore.unifi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {
    // Store Date Formatter.
    private static SimpleDateFormat formatIO = new SimpleDateFormat("hh:mm:ss");

    public static void print(LogType typeIO, String messageIO) {
        System.out.println("[" + LogHandler.formatIO.format(new Date()) + "] " + typeIO.toString() + " -> " + messageIO);
    }
}
