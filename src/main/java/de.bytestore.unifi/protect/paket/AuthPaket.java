package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;

public class AuthPaket extends ProtectPaket {
    public AuthPaket(JsonObject dataIO) {
        // Set ParamAgreement Channel.
        this.setChannel("ubnt_avclient_auth");

        // Set Payload Data.
        this.setData(dataIO);
    }
}
