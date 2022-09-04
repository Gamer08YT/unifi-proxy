package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;
import de.bytestore.unifi.provider.CamProvider;

public class NetworkPaket extends ProtectPaket {
    public NetworkPaket(int stateIO, CamProvider providerIO) {
        // Set Hello Channel.
        this.setChannel("NetworkStatus");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        // Set Network Information.
        objectIO.addProperty("connectionState", stateIO);
        objectIO.addProperty("connectionStateDescription", "CONNECTED");
        objectIO.addProperty("defaultInterface", "eth0");
        objectIO.addProperty("dhcpLeasetime", 86400);
        objectIO.addProperty("dnsServer", "8.8.8.8 4.2.2.2");
        objectIO.addProperty("gateway", "192.168.103.1");
        objectIO.addProperty("ipAddress", providerIO.getIP());
        objectIO.addProperty("linkDuplex", 1);
        objectIO.addProperty("linkSpeedMbps", 1000);
        objectIO.addProperty("mode", "dhcp");
        objectIO.addProperty("networkMask", "255.255.255.0");

        // Set Payload Data.
        this.setData(objectIO);
    }
}
