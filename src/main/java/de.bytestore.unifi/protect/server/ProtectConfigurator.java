package de.bytestore.unifi.protect.server;

import com.sun.net.httpserver.HttpsConfigurator;

import javax.net.ssl.SSLContext;

public class ProtectConfigurator extends HttpsConfigurator {
    /**
     * Creates a Https configuration, with the given {@link SSLContext}.
     *
     * @param context the {@code SSLContext} to use for this configurator
     * @throws NullPointerException if no {@code SSLContext} supplied
     */
    public ProtectConfigurator(SSLContext context) {
        super(context);
    }
}
