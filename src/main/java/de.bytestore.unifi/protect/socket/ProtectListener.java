package de.bytestore.unifi.protect.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import de.bytestore.unifi.protect.paket.AdoptionPaket;
import de.bytestore.unifi.protect.paket.AuthPaket;
import de.bytestore.unifi.protect.paket.NetworkPaket;
import de.bytestore.unifi.protect.paket.ParamAgreementPaket;
import de.bytestore.unifi.provider.CamProvider;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;

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
        // Print Debug Message.
        LogHandler.print(LogType.SUCCESS, "Connected to Protect WS Server");

        // Send Adoption Paket (if not Adopted).
        this.providerIO.sendPayload(new AdoptionPaket(this.providerIO.getToken(), this.providerIO));

        // Print Debug Message.
        LogHandler.print(LogType.INFO, "Adoption to Protect with Token '" + this.providerIO.getToken() + "' and Mac '" + this.providerIO.getMac() + "'.");
    }


    @Override
    public void onDisconnected(WebSocket websocketIO, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        // Print Debug Message.
        LogHandler.print(LogType.WARNING, "Disconnected from Protect WS Server.");
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

            // Get bool if Response Expected.
            boolean expectedIO = (objectIO.has("responseExpected") ? objectIO.get("responseExpected").getAsBoolean() : false);

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
                case "ubnt_avclient_auth":
                    // Print Server Auth Response.
                    LogHandler.print(LogType.SERVER, "UniFi Controller send Client Auth.");

                    this.providerIO.sendPayload(new AuthPaket(payloadIO), messageIO);
                    break;
                case "NetworkStatus":
                    this.providerIO.sendPayload(new NetworkPaket(2, this.providerIO), messageIO);
                    break;
                case "ChangeVideoSettings":
                    this.providerIO.sendPayload("ChangeVideoSettings", payloadIO, messageIO);
                    break;
                default:
                    LogHandler.print(LogType.SERVER, "Function Handler for '" + functionIO + "' is not implemented.");
                    LogHandler.print(LogType.INFO, "Payload: '" + payloadIO + "'");
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
