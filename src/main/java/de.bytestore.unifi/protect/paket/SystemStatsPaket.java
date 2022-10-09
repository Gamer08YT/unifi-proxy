package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;
import de.bytestore.unifi.provider.CamProvider;

public class SystemStatsPaket extends ProtectPaket{
    public SystemStatsPaket(CamProvider providerIO) {
        // Set Hello Channel.
        setChannel("GetSystemStats");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        objectIO.addProperty("connectionHost", providerIO.getHost());
        objectIO.addProperty("connectionSecurePort", providerIO.getPort());
        objectIO.addProperty("fwVersion", providerIO.getFirmware());
        objectIO.addProperty("hwrev", 19);
        objectIO.addProperty("idleTime", 191.96);
        objectIO.addProperty("ip", providerIO.getIP());
        objectIO.addProperty("mac", providerIO.getMac());
        objectIO.addProperty("model", providerIO.getModel().getName());
        objectIO.addProperty("name", providerIO.getName());
        objectIO.addProperty("protocolVersion", 67);
        objectIO.addProperty("rebootTimeoutSec", 30);
        objectIO.addProperty("semver", "v4.4.8");
        objectIO.addProperty("totalLoad", 0.5474);
        objectIO.addProperty("upgradeTimeoutSec", 150);
        objectIO.addProperty("uptime", providerIO.getUptime());
        objectIO.add("features", providerIO.getFeatures());

        // Set Payload Data.
        setData(objectIO);
    }
}
