package de.bytestore.unifi.protect;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class ProtectProvider {
    // Store HTTP Client of Protect.
    public static HttpClientBuilder clientIO = HttpClients.custom();
}
