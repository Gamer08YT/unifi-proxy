package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;

public class ParamAgreementPaket extends ProtectPaket {
    public ParamAgreementPaket(String tokenIO, JsonObject featuresIO) {
        // Set ParamAgreement Channel.
        this.setChannel("ubnt_avclient_paramAgreement");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        // Set Auth Token and Features.
        objectIO.addProperty("authToken", tokenIO);
        objectIO.add("features", featuresIO);

        // Set Payload Data.
        this.setData(objectIO);
    }
}
