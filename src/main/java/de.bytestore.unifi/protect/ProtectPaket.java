package de.bytestore.unifi.protect;

import com.google.gson.JsonObject;
import org.bytedeco.opencv.presets.opencv_core;

public class ProtectPaket {
    private String channelIO = "ubnt_avclient_hello";

    private JsonObject dataIO;

    public String getChannel() {
        return channelIO;
    }

    /**
     * Set Channel of API.
     * @param channelIO
     */
    public void setChannel(String channelIO) {
        this.channelIO = channelIO;
    }

    public JsonObject getData() {
        return dataIO;
    }

    /**
     * Set Data of API Request.
     * @param dataIO
     */
    public void setData(JsonObject dataIO) {
        this.dataIO = dataIO;
    }
}
