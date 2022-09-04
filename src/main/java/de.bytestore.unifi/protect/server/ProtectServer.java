package de.bytestore.unifi.protect.server;

import com.sun.net.httpserver.HttpsServer;
import de.bytestore.unifi.protect.tls.ProtectSSLContext;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executors;

public class ProtectServer {
    // Store WebServer of Client.
    private HttpsServer serverIO = null;

    public ProtectServer(int portIO) {
        try {
            // Print Debug Message.
            LogHandler.print(LogType.INFO, "Init new WebServer Socket.");

            // Create new HTTPServer.
            this.serverIO = HttpsServer.create(new InetSocketAddress(portIO), 0);

            // Configure SSL/TLS of WebServer.
            this.serverIO.setHttpsConfigurator(new ProtectConfigurator(new ProtectSSLContext().ProtectContext()));

            // Add Context Handler.
            this.serverIO.createContext("/", new ProtectContext());

            // Create Default Executor.
            this.serverIO.setExecutor(Executors.newCachedThreadPool());

            // Start HTTP Server.
            this.serverIO.start();

            // Print Debug Message.
            LogHandler.print(LogType.SUCCESS, "Started Protect WebServer on Port " + portIO + ".");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }
}
