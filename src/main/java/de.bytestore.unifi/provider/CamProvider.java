package de.bytestore.unifi.provider;

import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;
import de.bytestore.unifi.protect.paket.ProtectPaket;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;

import java.util.Date;

public class CamProvider implements CamProviderInterface {
    // Store Adoption Token.
    private String tokenIO;

    // Store Firmware Version of Camera.
    private String firmwareIO = "0.2.0";

    // Store Connection Host.
    private String hostIO = "192.168.1.110";

    // Store Connection Port (TLS).
    private int portIO = 7442;

    // Store Mac Address.
    private String macIO = "AA:BB:CC:DD:EE:DD";

    // Store Camera Model.
    private CamType modelIO = CamType.UVC_G3_FLEX;

    // Store Name of Camera.
    private String nameIO = "Unknown";

    // Store Timestamp for Uptime.
    Date dateIO = new Date();

    // Store Feature Flags of Camera.
    private CamFeatures featuresIO = new CamFeatures();

    // Store WebSocket of Client.
    private com.neovisionaries.ws.client.WebSocket websocketIO = null;

    // Store last Message ID.
    private int idIO = 0;


    @Override
    public void recordSnapshot() {

    }

    @Override
    public void getSnapshot() {

    }

    public String getHost() {
        return hostIO;
    }

    public int getPort() {
        return portIO;
    }

    public String getMac() {
        return macIO;
    }

    public CamType getModel() {
        return modelIO;
    }

    public String getName() {
        return nameIO;
    }

    public Date getDate() {
        return dateIO;
    }

    public long getUptime() {
        return (new Date().getTime() - this.dateIO.getTime());
    }

    public String getFirmware() {
        return firmwareIO;
    }

    public JsonObject getFeatures() {
        return featuresIO.CamFeatures();
    }

    public String getToken() {
        return tokenIO;
    }

    public void setToken(String tokenIO) {
        this.tokenIO = tokenIO;
    }

    public void sendPayload(ProtectPaket paketIO) {
        this.sendPayload(paketIO, 0);
    }

    public void sendPayload(ProtectPaket paketIO, int idIO) {
        this.sendPayload(paketIO.getChannel(), idIO, paketIO.getData());
    }


    public void sendPayload(String nameIO, int responseIO, JsonObject payloadIO) {
        // Create new JsonObject for Paket.
        JsonObject paketIO = new JsonObject();

        // Set some Container around the Payload.
        paketIO.addProperty("from", "ubnt_avclient");
        paketIO.addProperty("functionName", nameIO);
        paketIO.addProperty("inResponseTo", responseIO);
        paketIO.addProperty("messageId", this.generateID());
        paketIO.add("payload", payloadIO);
        paketIO.addProperty("responseExpected", false);
        paketIO.addProperty("to", "UniFiVideo");

        // Print Debug Message.
        LogHandler.print(LogType.SOCKET, "Preparing Channel '" + nameIO + "' for sending to UniFi Controller. (" + this.idIO + ")");

        // Write Paket to UniFi Protect.
        this.websocketIO.sendText(paketIO.toString());
    }

    private int generateID() {
        return this.idIO++;
    }

    public void setWebsocket(WebSocket websocketIO) {
        this.websocketIO = websocketIO;
    }
}
