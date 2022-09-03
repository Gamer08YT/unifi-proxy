package de.bytestore.unifi.provider;

public interface CamProviderInterface {
    /**
     * Record Stream to Disk/Memory.
     */
    void recordSnapshot();

    /**
     * Get Recorded Snapshot Stream from Disk/Memory.
     */
    void getSnapshot();
}
