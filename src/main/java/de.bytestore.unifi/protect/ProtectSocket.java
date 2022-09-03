package de.bytestore.unifi.protect;

import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import de.bytestore.unifi.provider.CamProvider;
import de.bytestore.unifi.secure.TrustManagerDummy;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;
import org.bytedeco.opencv.presets.opencv_core;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;

public class ProtectSocket {
    // Store WebSocket of Client.
    private com.neovisionaries.ws.client.WebSocket websocketIO = null;

    // Store Cam Provider of Protect Cam.
    private CamProvider providerIO;

    public ProtectSocket(URI urlIO, CamProvider providerIO) {
        this.providerIO = providerIO;

        try {
            // Create new WebSocketFactory.
            WebSocketFactory factoryIO = new WebSocketFactory();

            // Get Context Defaults from System JRE.
            SSLContext contextIO = SSLContext.getInstance("TLSv1.3");

            // Create new KeyManagerFactory.
            KeyManagerFactory managerIO = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            // Init new KeyStore Instance.
            KeyStore storeIO = KeyStore.getInstance(KeyStore.getDefaultType());

            // Load KeyStore from File.
            storeIO.load(this.getClass().getClassLoader().getResourceAsStream("client.jks"), "".toCharArray());

            // Init KeyManagerFactory
            managerIO.init(storeIO, "".toCharArray());

            // Init new Dummy Trust Manager.
            contextIO.init(managerIO.getKeyManagers(), new TrustManagerDummy[]{new TrustManagerDummy()}, SecureRandom.getInstanceStrong());

            // Print Debug Message.
            LogHandler.print(LogType.SUCCESS, "Init new TrustManagerDummy for ignoring wrong Certs.");

            // Set SSLContext to WebSocketFactory.
            factoryIO.setSSLSocketFactory(contextIO.getSocketFactory());

            // Disable Hostname Verification.
            factoryIO.setVerifyHostname(false);

            // Store URL of API.
            String apiIO = urlIO + "?token=" + providerIO.getToken();

            // Create new WebSocket from Factory.
            this.websocketIO = factoryIO.createSocket(apiIO, 10);

            // Set Headers.
            //this.websocketIO.addHeader("camera-fingerprint", "FD:91:4A:43:F6:50:74:61:24:20:AC:B5:24:F7:57:F6:00:A7:1F:A2");
            this.websocketIO.addHeader("camera-mac", this.providerIO.getMac());

            // Add Listener to WebSocket.
            this.websocketIO.addListener(new ProtectListener(providerIO));

            // Set WebSocket of Provider.
            this.providerIO.setWebsocket(this.websocketIO);

            // Print Debug Message.
            LogHandler.print(LogType.INFO, "Connecting to UniFi API on URL '" + apiIO + "'.");

            // Connect to WebSocket Server.
            this.websocketIO.connect();

            // Print Debug Message.
            LogHandler.print(LogType.SUCCESS, "Connected successfully to UniFi API.");
        } catch (NoSuchAlgorithmException | KeyManagementException | CertificateException | KeyStoreException |
                 IOException e) {
            System.out.println(e.getMessage());
        } catch (WebSocketException causeIO) {
            String messageIO = causeIO.getMessage();

            // Check for Error Code in HTTP Header.
            if (messageIO.contains("403")) {
                // Print Error Message.
                LogHandler.print(LogType.ERROR, "Invalid Adoption Token please generate new Token.");
            } else
                causeIO.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }

    }

    public com.neovisionaries.ws.client.WebSocket getWebsocket() {
        return websocketIO;
    }
}
