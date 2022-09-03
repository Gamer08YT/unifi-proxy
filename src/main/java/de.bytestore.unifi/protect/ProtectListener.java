package de.bytestore.unifi.protect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import de.bytestore.unifi.provider.CamProvider;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;
import org.bytedeco.opencv.presets.opencv_core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class ProtectListener extends WebSocketAdapter {
    // Store Cam Provider of Protect Cam.
    private CamProvider providerIO;

    public ProtectListener(CamProvider providerIO) {
        this.providerIO = providerIO;
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        ProtectPaket paketIO = new AdoptionPaket(this.providerIO.getToken());
        this.providerIO.sendPayload(paketIO);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
    }

    @Override
    public void onTextMessage(WebSocket websocketIO, String textIO) throws Exception {
        System.out.println(textIO);
        super.onTextMessage(websocketIO, textIO);
    }

    @Override
    public void onBinaryMessage(WebSocket websocketIO, byte[] binaryIO) throws Exception {
        try {
            // Parse String from Binary Frame.
            String dataIO = new String(binaryIO, Charset.defaultCharset());

            // Generate new GSON Instance.
            Gson gsonIO = new Gson();

            // Parse JsonObject from Text.
            JsonObject objectIO = gsonIO.fromJson(dataIO, JsonObject.class);

            // Get Function from Event.
            String functionIO = objectIO.get("functionName").getAsString();

            // Get Message ID from Event.
            int messageIO = objectIO.get("messageId").getAsInt();

            // Get Payload from Paket.
            JsonObject payloadIO = objectIO.get("payload").getAsJsonObject();

            // Switch for Function Names.
            switch (functionIO) {
                case "ubnt_avclient_hello":
                    // Print Server Hello Response.
                    LogHandler.print(LogType.SERVER, "UniFi Controller '" + payloadIO.get("controllerName").getAsString() + "' with Version '" + payloadIO.get("controllerVersion").getAsString() + "' responded.");
                    break;
                case "ubnt_avclient_paramAgreement":
                    // Sending ParamAgreement back to Server.
                    this.providerIO.sendPayload(new ParamAgreementPaket(this.providerIO.getToken(), this.providerIO.getFeatures()), messageIO);
                    break;
                default:
                    System.out.println("Function Handler for '" + functionIO + "' is not implemented.");
                    break;
            }

        } catch (Exception exceptionIO) {
            exceptionIO.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        cause.printStackTrace();

        super.onError(websocket, cause);
    }


}
