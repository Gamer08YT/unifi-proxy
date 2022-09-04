package de.bytestore.unifi.protect.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProtectContext implements HttpHandler {
    @Override
    public void handle(HttpExchange exchangeIO) throws IOException {
        // Read Data From Body.
        BufferedReader readerIO = new BufferedReader(new InputStreamReader(exchangeIO.getRequestBody()));

        // Store last read Byte.
        int charIO;

        // Store Strings in StringBuilder.
        StringBuilder builderIO = new StringBuilder();

        // Read Lines from Buffer.
        while ((charIO = readerIO.read()) != -1) {
            // Append Char from Byte.
            builderIO.append((char) charIO);
        }

        System.out.println(exchangeIO.getRequestURI());
        System.out.println(builderIO.toString());
    }
}
