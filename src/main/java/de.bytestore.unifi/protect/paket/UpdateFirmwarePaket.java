package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;
import de.bytestore.unifi.provider.CamProvider;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.ByteBuffer;

import static de.bytestore.unifi.protect.ProtectProvider.clientIO;

public class UpdateFirmwarePaket extends ProtectPaket {
    public UpdateFirmwarePaket(JsonObject payloadIO, CamProvider providerIO) {
        if (payloadIO.has("uri")) {
            // Create new HTTP Request.
            HttpGet getIO = new HttpGet(payloadIO.get("uri").getAsString());

            try {
                // Execute Get Request.
                HttpResponse responseIO = clientIO.build().execute(getIO);

                // Parse ByteArray from Response.
                ByteBuffer dataIO = ByteBuffer.wrap(EntityUtils.toByteArray(responseIO.getEntity()));

                // Store Version in String.
                String versionIO = "";

                // Read exactly 50 Bytes with 4 Offset (Only Update Branch eq. UBNTUVC).
                for (int intIO = 4; intIO < 54; intIO++) {
                    // Read Letter from Byte.
                    Character letterIO = (char) (dataIO.get(intIO));

                    // Check if Char is not empty.
                    if (dataIO.get(intIO) != 0) {
                        // Add Char to Version.
                        versionIO += letterIO;
                    }
                }

                // Set Firmware Version.
                providerIO.setFirmware(versionIO);

                // Print Debug Message.
                LogHandler.print(LogType.SUCCESS, "Switched Firmware Version to " + versionIO + ".");
            } catch (IOException e) {
                // Print Debug Message.
                LogHandler.print(LogType.ERROR, "Can't execute Update Request..");
            }


        } else {
            // Print Debug Message.
            LogHandler.print(LogType.ERROR, "Missing URI in Update Paket.");
        }

    }
}
