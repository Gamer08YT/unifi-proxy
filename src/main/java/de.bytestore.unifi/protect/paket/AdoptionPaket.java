package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;
import de.bytestore.unifi.provider.CamProvider;

public class AdoptionPaket extends ProtectPaket {
    // Store Camera of Paket.
    CamProvider providerIO = new CamProvider();

    public AdoptionPaket(String tokenIO) {
        // Set Hello Channel.
        this.setChannel("ubnt_avclient_hello");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        objectIO.addProperty("adoptionCode", tokenIO);
        objectIO.addProperty("connectionHost", "192.168.1.110");
        objectIO.addProperty("connectionSecurePort", this.providerIO.getPort());
        objectIO.addProperty("fwVersion", this.providerIO.getFirmware());
        objectIO.addProperty("hwrev", 19);
        objectIO.addProperty("idleTime", 191.96);
        objectIO.addProperty("ip", this.providerIO.getHost());
        objectIO.addProperty("mac", this.providerIO.getMac());
        objectIO.addProperty("model", this.providerIO.getModel().getName());
        objectIO.addProperty("name", this.providerIO.getName());
        objectIO.addProperty("protocolVersion", 67);
        objectIO.addProperty("rebootTimeoutSec", 30);
        objectIO.addProperty("semver", "v4.4.8");
        objectIO.addProperty("totalLoad", 0.5474);
        objectIO.addProperty("upgradeTimeoutSec", 150);
        objectIO.addProperty("uptime", this.providerIO.getUptime());
        objectIO.add("features", this.providerIO.getFeatures());

        // Set Payload Data.
        this.setData(objectIO);
    }
}
