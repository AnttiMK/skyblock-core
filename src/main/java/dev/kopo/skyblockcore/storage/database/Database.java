package dev.kopo.skyblockcore.storage.database;

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
