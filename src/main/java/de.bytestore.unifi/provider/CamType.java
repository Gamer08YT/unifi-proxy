package de.bytestore.unifi.provider;

public enum CamType {
    UVC_G3("UVC G3"),
    UVC_G3_DOME("UVC G3 Dome"),
    UVC_G3_MICRO("UVC G3 Micro"),
    UVC_G3_INSTANT("UVC G3 Instant"),
    UVC_G3_PRO("UVC G3 Pro"),
    UVC_G3_FLEX("UVC G3 Flex"),
    UVC_G3_BULLET("UVC G4 Bullet"),
    UVC_G4_PRO("UVC G4 Pro"),
    UVC_G4_PTZ("UVC G4 PTZ"),
    UVC_G4_DOORBELL("UVC G4 Doorbell"),
    UVC_G4_DOME("UVC G4 Dome");

    // Store Name of Enum Identifier.
    private String nameIO;

    CamType(String nameIO) {
        this.nameIO = nameIO;
    }

    public String getName() {
        return nameIO;
    }
}
