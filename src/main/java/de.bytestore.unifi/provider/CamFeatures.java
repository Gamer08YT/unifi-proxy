package de.bytestore.unifi.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CamFeatures {
    public JsonObject CamFeatures() {
        JsonObject featuresIO = new JsonObject();

        // Add Mic Feature.
        featuresIO.addProperty("mic", true);

        // Add AEC.
        featuresIO.add("aec", new JsonArray());

        // Create new Array for Video Mode and add Default Mode.
        JsonArray modeIO = new JsonArray();
        modeIO.add("default");

        // Add Video Mode.
        featuresIO.add("videoMode", modeIO);

        // Create new Array for Motion Mode and add Default Mode.
        JsonArray motionIO = new JsonArray();
        motionIO.add("enhancedt");

        // Add Motion Mode.
        featuresIO.add("motionDetect", motionIO);

        return featuresIO;
    }
}
