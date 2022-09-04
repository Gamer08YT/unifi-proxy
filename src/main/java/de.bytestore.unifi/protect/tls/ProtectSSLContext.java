package de.bytestore.unifi.protect.tls;

import de.bytestore.unifi.secure.TrustManagerDummy;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

public class ProtectSSLContext {
    public SSLContext ProtectContext() throws NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        // Get Context Defaults from System JRE.
        SSLContext contextIO = SSLContext.getInstance("TLSv1.2");

        // Create new KeyManagerFactory.
        KeyManagerFactory managerIO = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

        // Init new KeyStore Instance.
        KeyStore storeIO = KeyStore.getInstance(KeyStore.getDefaultType());

        // Get Stream from Resource.
        InputStream keystoreIO = this.getClass().getClassLoader().getResourceAsStream("client.jks");

        // Print Error Message if ClassLoader can't find Keystore.
        if (keystoreIO == null)
            LogHandler.print(LogType.ERROR, "Can't read client.jks");

        // Load KeyStore from File.
        storeIO.load(keystoreIO, "".toCharArray());

        // Init KeyManagerFactory
        managerIO.init(storeIO, "".toCharArray());

        // Init new Dummy Trust Manager.
        contextIO.init(managerIO.getKeyManagers(), new TrustManagerDummy[]{new TrustManagerDummy()}, SecureRandom.getInstanceStrong());

        // Print Debug Message.
        LogHandler.print(LogType.SUCCESS, "Init new TrustManagerDummy for ignoring wrong Certs.");


        return contextIO;
    }
}
