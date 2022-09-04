package de.bytestore.unifi.provider.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ValidFPS {
    public JsonArray ValidFPS() {
        JsonArray fpsIO = new JsonArray();

        // Add Hardcoded FPS Values.
        fpsIO.add(1);
        fpsIO.add(2);
        fpsIO.add(3);
        fpsIO.add(4);
        fpsIO.add(5);
        fpsIO.add(6);
        fpsIO.add(7);
        fpsIO.add(8);
        fpsIO.add(9);
        fpsIO.add(10);
        fpsIO.add(12);
        fpsIO.add(15);
        fpsIO.add(16);
        fpsIO.add(18);
        fpsIO.add(20);
        fpsIO.add(24);
        fpsIO.add(25);
        fpsIO.add(30);

        return fpsIO;
    }
}
