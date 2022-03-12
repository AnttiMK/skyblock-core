package net.motimaa.skyblockcore.storage.database;

public interface Database {

    void initialize();

    void shutdown();

    DBState getState();

    enum DBState {
        OPEN,
        CLOSED,
        CLOSING,
        MIGRATING
    }
}
