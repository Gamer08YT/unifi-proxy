package de.bytestore.unifi;

import de.bytestore.unifi.protect.server.ProtectServer;
import de.bytestore.unifi.protect.socket.ProtectSocket;
import de.bytestore.unifi.provider.CamProvider;

import java.net.URI;
import java.net.URISyntaxException;

public class UniFi {
    public static void main(String[] args) {
        String tokenIO = "GBXbjFgh3Ff05byGi1ChXBJh5ixYrim2";
        CamProvider providerIO = new CamProvider();
        providerIO.setToken(tokenIO);

        try {
            new ProtectServer(443);
            ProtectSocket socketIO = new ProtectSocket(new URI("wss://192.168.1.110:7442/camera/1.0/ws"), providerIO);
            System.out.println("SEND");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
