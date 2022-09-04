package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;
import de.bytestore.unifi.provider.CamProvider;

public class DeviceSettings extends ProtectPaket {
    public DeviceSettings() {
        // Set Hello Channel.
        this.setChannel("NetworkStatus");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        // Set new Name and Timezone.
        objectIO.addProperty("name", "Bytestore");
        objectIO.addProperty("timezone", "PST8PDT,M3.2.0,M11.1.0");

        // Set Payload Data.
        this.setData(objectIO);
    }
}
