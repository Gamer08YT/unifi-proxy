package de.bytestore.unifi;

import de.bytestore.unifi.protect.server.ProtectServer;
import de.bytestore.unifi.protect.socket.ProtectSocket;
import de.bytestore.unifi.protect.tls.ProtectSSLContext;
import de.bytestore.unifi.provider.CamProvider;
import de.bytestore.unifi.provider.RTSPCam;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static de.bytestore.unifi.protect.ProtectProvider.clientIO;

public class UniFi {

    private static ProtectSocket socketIO;
    public static RTSPCam camIO;

    // Store Thread Pool.
    public static ExecutorService poolIO = Executors.newCachedThreadPool();

    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, KeyManagementException {
        // Set SSLSocketFactory.
        clientIO.setSSLContext(new ProtectSSLContext().ProtectContext());

        // Disable Hostname Verification.
        clientIO.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        String tokenIO = "GBXbjFgh3Ff05byGi1ChXBJh5ixYrim2";
        CamProvider providerIO = new CamProvider();
        providerIO.setToken(tokenIO);

        new ProtectServer(443);


        // Submit RTSP Cam as Thread.
        poolIO.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    UniFi.camIO = new RTSPCam();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Submit Protect Socket Cam as Thread.
        poolIO.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    UniFi.socketIO = new ProtectSocket(new URI("wss://192.168.1.110:7442/camera/1.0/ws"), providerIO);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        System.out.println("SEND");
    }
}
